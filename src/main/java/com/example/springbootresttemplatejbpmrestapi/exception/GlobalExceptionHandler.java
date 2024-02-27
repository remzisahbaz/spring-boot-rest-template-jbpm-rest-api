package com.example.springbootresttemplatejbpmrestapi.exception;

import com.example.springbootresttemplatejbpmrestapi.model.ErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorHandler> handleHttpClientErrorException(HttpClientErrorException ex) {
        ErrorHandler errorHandler= ErrorHandler.builder()
                .title(ex.getStatusCode().toString())
                .errorStatusNo(ex.getStatusCode().value())
                .errorMessage(ex.getResponseBodyAsString())
                .build();
        return new ResponseEntity<>(errorHandler, HttpStatusCode.valueOf(errorHandler.getErrorStatusNo()));
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorHandler> handleHttpServerErrorException(HttpServerErrorException ex) {
        ErrorHandler errorHandler= ErrorHandler.builder()
                .title(ex.getStatusCode().toString())
                .errorStatusNo(ex.getStatusCode().value())
                .errorMessage(ex.getResponseBodyAsString())
                .build();
        return new ResponseEntity<>(errorHandler, HttpStatusCode.valueOf(errorHandler.getErrorStatusNo()));
    }
}
