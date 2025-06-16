package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.exception.InvalidEmailFoundException;
import com.example.airbnb.exception.InvalidPasswordException;
import com.example.airbnb.exception.UserAlreadyExistsException;
import com.example.airbnb.services.serviceUtils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern ALPHANUMERIC_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]*$");

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        checkIfUserExists(request.getEmail(), request.getUsername());
        validateEmailFormat(request.getEmail());
        validatePasswordStrength(request.getPassword());

        User user = createUserFromRequest(request);
        userRepository.save(user);

        return buildRegistrationResponse(user);
    }

    private void checkIfUserExists(String email, String username) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new UserAlreadyExistsException("A user with this email already exists");
        });

        userRepository.findByUsername(username).ifPresent(u -> {
            throw new UserAlreadyExistsException("A user with this username already exists");
        });
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailFoundException("Invalid email format");
        }
    }

    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!ALPHANUMERIC_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordException("Password must be alphanumeric");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password must contain at least one digit");
        }
    }

    private User createUserFromRequest(RegisterUserRequest request) {
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // secure hashing
                .build();
    }

    private RegisterUserResponse buildRegistrationResponse(User user) {
        return RegisterUserResponse.builder()
                .username(user.getUsername())
                .message("User registered successfully")
                .build();
    }
}
