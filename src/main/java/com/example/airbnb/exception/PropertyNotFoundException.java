package com.example.airbnb.exception;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String e) {
        super(e);
    }
}
