package com.example.airbnb.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
}
