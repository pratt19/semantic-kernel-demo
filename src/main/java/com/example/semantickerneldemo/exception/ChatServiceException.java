package com.example.semantickerneldemo.exception;

public class ChatServiceException extends RuntimeException {
    public ChatServiceException(String message) {
        super(message);
    }

    public ChatServiceException(String message, Throwable cause) {
        super(message, cause);
    }
} 