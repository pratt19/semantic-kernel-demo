package com.example.semantickerneldemo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ModelServiceException.class)
    public ResponseEntity<ErrorResponse> handleModelServiceException(ModelServiceException e) {
        log.error("Model service error", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Model service error", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Unexpected error", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Internal server error", "An unexpected error occurred"));
    }

    record ErrorResponse(String error, String message) {}
} 