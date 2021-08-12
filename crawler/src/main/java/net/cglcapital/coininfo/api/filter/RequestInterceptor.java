package net.cglcapital.coininfo.api.filter;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.BodyRequestWrapper;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

import static net.cglcapital.coininfo.common.constant.HeaderConstant.AUTHORIZATION;
import static net.cglcapital.coininfo.common.constant.HeaderConstant.X_REQUEST_ID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestInterceptor implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // ignore implementation for this function
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        try {
            BodyRequestWrapper requestWrapper = new BodyRequestWrapper(request);

            String requestId = Optional.ofNullable(request.getHeader(X_REQUEST_ID)).orElse(UUID.randomUUID().toString());

            // put request id to MDC for tracing log
            MDC.put(X_REQUEST_ID, requestId);
            request.setAttribute("request_id", requestId);
            // add request id into response header
            ((HttpServletResponse) res).addHeader(X_REQUEST_ID, requestId);

            Enumeration<String> headerNames = request.getHeaderNames();
            StringBuilder headerBuilder = new StringBuilder();
            while (headerNames.hasMoreElements()) {
                String element = headerNames.nextElement();
                String elemValue = request.getHeader(element);

                if (!element.equalsIgnoreCase(AUTHORIZATION.toLowerCase())) {
                    headerBuilder.append(element).append(": ").append(elemValue).append(" ");
                    MDC.put(element, elemValue);
                } else {
                    headerBuilder.append(element).append(": ").append("*****").append(" ");
                }
            }

            MDC.put("method", request.getMethod());
            log.info("Access: \"{} {}\" - Headers: \"{}\"", request.getMethod(), request.getRequestURI(), headerBuilder.toString());
            log.debug("Body request: \"{}\"", requestWrapper.getBody());

            long start = System.currentTimeMillis();
            chain.doFilter(requestWrapper, res);
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            MDC.put("elapsed_time", String.valueOf(elapsedTime));

            log.info("Response: {} - {}ms", response.getStatus(), elapsedTime);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } finally {
            MDC.clear(); // clear current MDC of this thread
        }
    }

    @Override
    public void destroy() {
        // ignore implementation for this function
    }
}
