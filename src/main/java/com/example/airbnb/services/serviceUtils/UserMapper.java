package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.data.model.User;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.dtos.responses.UserResponseDTO;

public class UserMapper {
    public static User createUserFromRegisterRequest(RegisterUserRequest request, String hashedPassword) {
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashedPassword)
                .build();
    }

    public static RegisterUserResponse toRegisterResponse(User user) {
        return RegisterUserResponse.builder()
                .username(user.getUsername())
                .message("User registered successfully")
                .build();
    }
    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .timeCreated(user.getTimeCreated())
                .timeUpdated(user.getTimeUpdated())
                .build();
    }
}
