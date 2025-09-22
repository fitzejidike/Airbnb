package com.example.airbnb.exception;

public class AssetDeletionDeniedException extends RuntimeException {
    public AssetDeletionDeniedException(String e) {
        super(e);
    }
}
