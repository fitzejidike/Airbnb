package com.example.airbnb.dtos.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private int code;

    // ✅ Success (with data, message, and code)
    public static <T> ApiResponse<T> success(T data, String message, int code) {
        return ApiResponse.<T>builder()
                .data(data)
                .success(true)
                .message(message)
                .code(code)
                .build();
    }

    // ✅ Success (defaults to 200 OK)
    public static <T> ApiResponse<T> success(T data, String message) {
        return success(data, message, 200);
    }

    //  Failure (just message, defaults to 400)
    public static <T> ApiResponse<T> failure(String message) {
        return failure(null, message, 400);
    }

    //  Failure (just message, custom code)
    public static <T> ApiResponse<T> failure(String message, int code) {
        return failure(null, message, code);
    }

    // ✅ Failure (with data, e.g., validation errors)
    public static <T> ApiResponse<T> failure(T data, String message, int code) {
        return ApiResponse.<T>builder()
                .data(data)
                .success(false)
                .message(message)
                .code(code)
                .build();
    }
}