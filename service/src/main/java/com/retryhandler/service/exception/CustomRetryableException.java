package com.retryhandler.service.exception;

public class CustomRetryableException extends Exception {
    public CustomRetryableException(String message) {
        super(message);
    }
}
