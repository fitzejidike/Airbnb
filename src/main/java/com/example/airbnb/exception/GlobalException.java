package com.example.airbnb.exception;

public class GlobalException extends RuntimeException {
    private GlobalException(String e) {
        super(e);
    }
}
