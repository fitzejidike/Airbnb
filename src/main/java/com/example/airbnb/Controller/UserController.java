package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.request.UserUpdateDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.dtos.responses.UserResponseDTO;
import com.example.airbnb.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserResponse>> register(
            @Valid @RequestBody RegisterUserRequest registerUserRequest
    ) {
        RegisterUserResponse response = userService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RegisterUserResponse>builder()
                        .data(response)
                        .success(true)
                        .message("User registered successfully")
                        .build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateDTO dto
    ) {
        UserResponseDTO response = userService.updateProfile(userId, dto);
        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .data(response)
                        .success(true)
                        .message("User profile updated successfully")
                        .build()
        );
    }
}
