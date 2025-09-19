package com.example.airbnb.dtos.responses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private int code; // Added to match filter's usage

    // Static factory for success responses
    public static <T> ApiResponse<T> success(T data, String message, int code) {
        return ApiResponse.<T>builder()
                .data(data)
                .success(true)
                .message(message)
                .code(code)
                .build();
    }

    // Static factory for failures
    public static <T> ApiResponse<T> failure(String message, int code) {
        return ApiResponse.<T>builder()
                .data(null)
                .success(false)
                .message(message)
                .code(code)
                .build();
    }
}