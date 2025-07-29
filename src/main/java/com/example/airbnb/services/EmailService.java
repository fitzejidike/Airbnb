package com.example.airbnb.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}

