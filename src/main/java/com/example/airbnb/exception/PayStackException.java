package com.example.airbnb.exception;


public class PayStackException extends RuntimeException {
    public PayStackException(String message) {
        super(message);
    }
    public PayStackException(String message, Throwable cause) {
        super(message, cause);
    }
}

