package net.cglcapital.coininfo.common.exception;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.model.BaseResponse;
import net.cglcapital.coininfo.common.model.Meta;
import net.cglcapital.coininfo.common.util.RequestResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static net.cglcapital.coininfo.common.constant.ErrorCode.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("Internal Server Error", e);
        Meta meta = new Meta(INTERNAL_SERVER_ERROR);
        BaseResponse response = new BaseResponse(meta);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.debug("Body request not readable");
        Meta meta = new Meta(WRONG_BODY_FORMAT);
        BaseResponse response = new BaseResponse(meta);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<BaseResponse> handleServiceException( ServiceException e) {
        Meta meta = new Meta(e.getError());
        BaseResponse response = new BaseResponse(meta);
        return new ResponseEntity<>(response, RequestResponseUtil.detectHttpStatus(e.getError()));
    }

}
