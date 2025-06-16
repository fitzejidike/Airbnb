package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.data.model.User;
import com.example.airbnb.dtos.responses.UserResponseDTO;

public class UserMapper {
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
