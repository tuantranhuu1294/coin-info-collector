package net.cglcapital.coininfo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import net.cglcapital.coininfo.common.exception.Error;
import net.cglcapital.coininfo.common.model.BaseResponse;
import net.cglcapital.coininfo.common.model.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class RequestResponseUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static HttpStatus detectHttpStatus(Error error) {
        if (error.getHttpCode() != null) return HttpStatus.valueOf(error.getHttpCode());

        String errorCode = String.valueOf(error.getCode());
        String httpCode = errorCode.substring(0, 3);

        return HttpStatus.valueOf(Integer.parseInt(httpCode));
    }

    public static void writeError(HttpServletResponse response, Error error) throws IOException {
        response.setStatus(detectHttpStatus(error).value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Meta meta = new Meta(error);
        BaseResponse baseResponse = new BaseResponse(meta);
        response.getOutputStream().write(restResponseBytes(baseResponse));
    }

    private static byte[] restResponseBytes(Object res) throws JsonProcessingException {
        String serialized = mapper.writeValueAsString(res);
        return serialized.getBytes();
    }
}
