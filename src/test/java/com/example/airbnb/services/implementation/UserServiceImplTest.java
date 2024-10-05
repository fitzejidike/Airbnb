package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.responses.RegisterUserResponse;
import com.example.airbnb.exception.UserAlreadyExistsException;
import com.example.airbnb.services.serviceUtils.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
   @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToRegister(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("fitzgerald ejidike");
        request.setUsername("Fitz94");
        request.setPassword("Password94");
        request.setEmail("fitz@gmail.com");
        RegisterUserResponse response = userService.register(request);
        assertNotNull(response);
        assertTrue(response.getMessage().equals("User registered successfully"));
        Assertions.assertEquals(1,userRepository.findAll().size());



    }
    @Test
    public void testToRegisterTwice() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("fitzgerald ejidike");
        request.setUsername("Fitz94");
        request.setPassword("Password94");
        request.setEmail("fitz@gmail.com");

        User existingUser = new User();
        existingUser.setEmail(request.getEmail());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(request);
        });
    }
}

