package com.example.airbnb.dtos.responses;

import com.cloudinary.AccessControlRule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String token;
    private String refreshToken;
    private  String message;


}
