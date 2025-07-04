package com.example.airbnb.dtos.responses;

import lombok.*;



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

    // ✅ Static factory for success responses
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .success(true)
                .message(message)
                .build();
    }

    // ✅ Optional: Static factory for failures (if you add error handling)
    public static <T> ApiResponse<T> failure(String message) {
        return ApiResponse.<T>builder()
                .data(null)
                .success(false)
                .message(message)
                .build();
    }
}

