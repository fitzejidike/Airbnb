package com.example.airbnb.exception;

public class InvalidEmailFoundException extends RuntimeException {
    public InvalidEmailFoundException(String e) {
        super(e);
    }
}
