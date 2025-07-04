package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.request.UserUpdateDTO;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.dtos.responses.UserResponseDTO;
import com.example.airbnb.exception.UserAlreadyExistsException;
import com.example.airbnb.exception.UserNotFoundException;
import com.example.airbnb.services.serviceUtils.InputValidators;
import com.example.airbnb.services.serviceUtils.UserMapper;
import com.example.airbnb.services.UserService;
import com.example.airbnb.services.serviceUtils.UserValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        UserValidators.checkIfUserExists(userRepository, request.getEmail(), request.getUsername());
       InputValidators.validateEmail(request.getEmail());
       InputValidators.validatePassword(request.getPassword());

        User user = UserMapper.createUserFromRegisterRequest(
                request,
                passwordEncoder.encode(request.getPassword())
        );

        return UserMapper.toRegisterResponse(user);
    }


    @Override
    public UserResponseDTO updateProfile(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getName() != null) user.setName(dto.getName());

        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(user.getEmail())) {
            InputValidators.validateEmail(dto.getEmail()); // reuse from register()
            user.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            userRepository.findByUsername(dto.getUsername())
                    .filter(u -> !u.getId().equals(userId))
                    .ifPresent(u -> { throw new UserAlreadyExistsException("Username is taken"); });

            user.setUsername(dto.getUsername());
        }

        user.setTimeUpdated(LocalDateTime.now());

        log.info("User with ID {} updated profile. Changed name={}, email={}, username={}",
                userId,
                dto.getName() != null,
                dto.getEmail() != null,
                dto.getUsername() != null && !dto.getUsername().equals(user.getUsername()));

        return UserMapper.toResponseDTO(userRepository.save(user));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponseDTO);
    }



}
