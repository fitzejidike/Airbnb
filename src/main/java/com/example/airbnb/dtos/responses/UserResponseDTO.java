package com.example.airbnb.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Builder
public class UserResponseDTO {
        private Long id;
        private String name;
        private String username;
        private String email;
        private LocalDateTime timeCreated;
        private LocalDateTime timeUpdated;
}
