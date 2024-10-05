package com.example.airbnb.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String e) {
        super(e);
    }
}
