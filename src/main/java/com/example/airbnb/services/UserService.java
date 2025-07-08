package com.example.airbnb.services;

import com.example.airbnb.data.model.User;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.request.UserUpdateDTO;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.dtos.responses.UserResponseDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface UserService {
 RegisterUserResponse register(RegisterUserRequest request);
    UserResponseDTO updateProfile(Long userId, UserUpdateDTO dto);
    User getUserById(Long userId);
    Page<UserResponseDTO> getAll(Pageable pageable);

}
