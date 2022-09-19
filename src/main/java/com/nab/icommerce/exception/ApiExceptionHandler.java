package com.nab.icommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(1)
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {APIException.class})
    protected ResponseEntity<Object> handleClientException(APIException ex) {
        ErrorItem item = buildFailErrorItem(ex.getErrorCode(), ex.getErrorMessage());
        log.error(ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.badRequest().body(item);
    }

    private ErrorItem buildFailErrorItem(String errorCode, String errorMessage) {
        ErrorItem errorItem = ErrorItem.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
        return errorItem;
    }
}
