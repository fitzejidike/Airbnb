package com.example.airbnb.dtos.responses;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {
    private Long id;
private String message;
private String username;
}
