package com.example.airbnb.paystack;

public interface PayStackHttpClient {
    String post(String url, okhttp3.RequestBody body);
    String get(String url);
}