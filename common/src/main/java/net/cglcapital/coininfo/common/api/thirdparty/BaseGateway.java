package net.cglcapital.coininfo.common.api.thirdparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static net.cglcapital.coininfo.common.constant.ErrorCode.EXTERNAL_SERVICE_AUTH_ERROR;
import static net.cglcapital.coininfo.common.constant.ErrorCode.INTERNAL_SERVER_ERROR;


@Slf4j
public abstract class BaseGateway {

    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Execute the HTTP method to the given URI template, writing the given request entity to the request, and
     * returns the response as {@link ResponseEntity}.
     * URI Template variables are expanded using the given URI variables, if any.
     * Note that this method only using for requests that have body as a JSON object
     *
     * @param url      the URL
     * @param method   the HTTP method (GET, PUT, POST, etc)
     * @param headers  the {@link HttpHeaders}
     * @param payload  body of the request, only accept JSON body
     * @param response class of the response
     * @param <T>      response object type
     * @return a ResponseEntity object
     */
    protected <T> ResponseEntity<T> jsonExchange(String url, HttpMethod method, HttpHeaders headers, Object payload, Class<T> response,
                                                 Object... uriVariables) {
        try {
            return exchange(url, method, headers, objectMapper.writeValueAsString(payload), response, uriVariables);
        } catch (JsonProcessingException e) {
            log.error("[" + serviceName() + "] Payload may not be JSON object", e);
            throw new ServiceException(INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Execute the HTTP method to the given URI template, writing the given request entity to the request, and
     * returns the response as {@link ResponseEntity}.
     * URI Template variables are expanded using the given URI variables, if any.
     *
     * @param url      the URL
     * @param method   the HTTP method (GET, PUT, POST, etc)
     * @param headers  the {@link HttpHeaders}
     * @param payload  body of the request
     * @param response class of the response
     * @param <T>      response object type
     * @return a ResponseEntity object
     */
    protected <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpHeaders headers, Object payload, Class<T> response,
                                             Object... uriVariables) {
        // filter sensitive data in headers for logging
        List<String> sensitiveHeaders = Collections.singletonList("authorization");
        StringBuilder headerStrBuilder = new StringBuilder();
        headers.toSingleValueMap().forEach((key, value) -> headerStrBuilder.append(key).append(": ").append(sensitiveHeaders.contains(key.toLowerCase()) ? "*****" : value).append(" "));

        try {
            HttpEntity<Object> request = new HttpEntity<>(payload, headers);

            log.debug("[{}] {}, Request URL: {}, Headers: {}, Body: {}", serviceName(), method, url, headerStrBuilder.toString(),
                objectMapper.writeValueAsString(payload));

            ResponseEntity<T> res = this.restTemplate.exchange(url, method, request, response, uriVariables);

            if (res == null || res.getBody() == null) {
                log.error("Aggregator server return NULL value");
                throw new ServiceException(INTERNAL_SERVER_ERROR);
            }
            log.debug("[{}] Response: headers: {}, code: {}, body: {}", serviceName(), objectMapper.writeValueAsString(res.getHeaders()),
                res.getStatusCodeValue(), objectMapper.writeValueAsString(res.getBody()));

            return res;
        } catch (ResourceAccessException e) {
            log.error("[{}] ResourceAccessException: {}", serviceName(), e.getMessage());
            throw new ServiceException(INTERNAL_SERVER_ERROR, serviceName() + ": Resource access exception");
        } catch (HttpClientErrorException ex) {
            log.error("[{}] HttpClientErrorException: {} - {}", serviceName(), ex.getMessage(), ex.getResponseBodyAsString());

            // handle wrong secret info
            if (ex.getRawStatusCode() == 401) {
                log.error("[{}] Please check HEADER info: {}", serviceName(), headerStrBuilder.toString());
            }

            throw new ServiceException(EXTERNAL_SERVICE_AUTH_ERROR, ex);
        } catch (HttpServerErrorException e) {
            log.error("[{}] HttpServerErrorException: {} - {}", serviceName(), e.getMessage(), e.getResponseBodyAsString());
            throw new ServiceException(INTERNAL_SERVER_ERROR, "External service \"" + serviceName() + "\" error");
        } catch (Exception e) {
            log.error("[" + serviceName() + "] Error when calling to " + url, e);
            throw new ServiceException(INTERNAL_SERVER_ERROR, e);
        }
    }


    /**
     * Return name of the service gateway
     *
     * @return String name
     */
    protected abstract String serviceName();

    protected HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
