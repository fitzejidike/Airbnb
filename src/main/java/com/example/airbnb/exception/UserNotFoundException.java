package com.example.airbnb.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String e) {
        super(e);
    }
}
