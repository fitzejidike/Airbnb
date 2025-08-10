package com.example.airbnb.dtos.request;

import com.example.airbnb.data.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role;


}
