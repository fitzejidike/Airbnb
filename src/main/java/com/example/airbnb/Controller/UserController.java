package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.request.UserUpdateDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.dtos.responses.UserResponseDTO;
import com.example.airbnb.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserResponse>> register(
            @RequestBody @Valid RegisterUserRequest registerRequest) {

        var response = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User registered successfully"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateDTO updateDTO) {

        var response = userService.updateProfile(userId, updateDTO);
        return ResponseEntity.ok(ApiResponse.success(response, "User profile updated successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getAllUsers(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<UserResponseDTO> users = userService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(users, "All users fetched with pagination"));
    }

}

