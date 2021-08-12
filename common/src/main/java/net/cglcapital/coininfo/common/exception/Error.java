package net.cglcapital.coininfo.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    protected Integer code;
    protected Integer httpCode;
    protected String errorCode;
    protected String serviceCode;
    protected String target;
    protected String message;
    protected String requestId;
    public List<Error> errors;

    public Error(Error error) {
        if (error != null) {
            this.code = error.getCode();
            this.httpCode = error.getHttpCode();
            this.errorCode = error.getErrorCode();
            this.serviceCode = error.getServiceCode();
            this.target = error.getTarget();
            this.message = error.getMessage();
            this.requestId = error.getRequestId();
            this.errors = error.getErrors();
        }
    }

    public Error(Error error, String message) {
        this(error);
        this.message = message;
    }

    public Error(Error error, List<Error> errors) {
        this(error);
        this.errors = errors;
    }

    public Error(Error parentError, Error subError) {
        this(parentError);
        if (this.errors == null) this.errors = new ArrayList<>();
        this.errors.add(subError);
    }

    public Error(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public Error(Integer httpCode, String errorCode, String serviceCode, String message) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.serviceCode = serviceCode;
        this.message = message;
    }

    public Error(Integer httpCode, String errorCode, String serviceCode, String message, String requestId) {
        this(httpCode, errorCode, serviceCode, message);
        this.requestId = requestId;
    }

    public Error(Integer code, Integer httpCode, String errorCode, String serviceCode, String message, String requestId) {
        this(code, httpCode, errorCode, serviceCode, message);
        this.requestId = requestId;
    }

    public Error(Integer httpCode, String errorCode, String serviceCode, String message, Error subError) {
        this(httpCode, errorCode, serviceCode, message);
        if (this.errors == null) this.errors = new ArrayList<>();
        this.errors.add(subError);
    }

    public Error(Integer code, Integer httpCode, String errorCode, String serviceCode, String message) {
        this.code = code;
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.serviceCode = serviceCode;
        this.message = message;
    }

    public Error(Integer code, Integer httpCode, String errorCode, String serviceCode, String message, Error subError) {
        this(code, httpCode, errorCode, serviceCode, message);
        if (this.errors == null) this.errors = new ArrayList<>();
        this.errors.add(subError);
    }
}
