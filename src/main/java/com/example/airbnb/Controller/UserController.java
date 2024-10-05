package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.services.serviceUtils.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.status(CREATED).body(new ApiResponse(userService.register
                (registerUserRequest),true));
    }
}
