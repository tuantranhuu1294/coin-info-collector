package net.cglcapital.coininfo.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.cglcapital.coininfo.common.exception.Error;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta extends Error {

    public Meta(Error error) {
        super(error);
    }

    public Meta(int httpCode, String errorCode, String serviceCode, String message) {
        super(httpCode, errorCode, serviceCode, message);
    }

    public Meta(int httpCode, String errorCode, String serviceCode, String message, String requestId) {
        super(httpCode, errorCode, serviceCode, message, requestId);
    }

    public Meta(int code, int httpCode, String errorCode, String serviceCode, String message, String requestId) {
        super(code, httpCode, errorCode, serviceCode, message, requestId);
    }

}
