package com.assignment.gateway.config;

import com.assignment.gateway.model.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<Map<String, String>> handleStorageException(GatewayException ex, WebRequest request){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.valueOf(ex.getStatusCode()));
    }

//    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherException(Exception ex, WebRequest request){
        return new ResponseEntity<>(Collections.singletonMap("message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
