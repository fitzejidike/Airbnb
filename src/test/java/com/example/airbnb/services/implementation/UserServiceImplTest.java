package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.exception.InvalidEmailFoundException;
import com.example.airbnb.exception.InvalidPasswordException;
import com.example.airbnb.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize @Mock and @InjectMocks
    }

    @Test
    void register_ValidRequest_ReturnsResponse() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Fitz");
        request.setUsername("fitz123");
        request.setEmail("fitz@example.com");
        request.setPassword("password1");

        Mockito.when(userRepository.findByEmail("fitz@example.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername("fitz123")).thenReturn(Optional.empty());


        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));


        RegisterUserResponse response = userService.register(request);


        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("fitz123");
        assertThat(response.getMessage()).isEqualTo("User registered successfully");
    }

    @Test
    void register_DuplicateEmail_ThrowsException() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("fitz@example.com");
        request.setUsername("fitz123");
        request.setPassword("password1");

        Mockito.when(userRepository.findByEmail("fitz@example.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(request));
    }

    @Test
    void register_DuplicateUsername_ThrowsException() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("fitz@example.com");
        request.setUsername("fitz123");
        request.setPassword("password1");

        Mockito.when(userRepository.findByEmail("fitz@example.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername("fitz123"))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(request));
    }

    @Test
    void register_InvalidEmail_ThrowsException() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("invalid-email");
        request.setUsername("fitz123");
        request.setPassword("password1");

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(InvalidEmailFoundException.class, () -> userService.register(request));
    }

    @Test
    void register_InvalidPassword_ThrowsException() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("fitz@example.com");
        request.setUsername("fitz123");
        request.setPassword("123"); // too short

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordException.class, () -> userService.register(request));
    }
}
