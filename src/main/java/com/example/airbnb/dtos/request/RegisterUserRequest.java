package com.example.airbnb.dtos.request;

import com.example.airbnb.data.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    @NotBlank private String name;
    @NotBlank private String email;
    @Email private String username;
    @NotBlank private String password;
    private Role role;


}
