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
}
