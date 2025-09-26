package com.example.airbnb.services.implementation;

import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.dtos.request.RegisterUserRequest;
import com.example.airbnb.dtos.request.UserUpdateDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private String jwtToken;
    private Long userId;

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();

        // Register a test user
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Auth User");
        registerRequest.setUsername("auth_user");
        registerRequest.setEmail("auth_user@example.com");
        registerRequest.setPassword("securePass123");

        MvcResult registerResult = mockMvc.perform(post("/api/v1/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract userId
        String registerJson = registerResult.getResponse().getContentAsString();
        JsonNode registerNode = objectMapper.readTree(registerJson);
        userId = registerNode.get("data").get("id").asLong();

        // Now login to get token
        String loginPayload = """
            {
              "username":"auth_user",
              "password":"securePass123"
            }
            """;

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(status().isOk())
                .andReturn();

        String loginJson = loginResult.getResponse().getContentAsString();
        JsonNode loginNode = objectMapper.readTree(loginJson);

        jwtToken = loginNode.get("data").get("token").asText(); // ✅ token now comes from login response
    }

    @Test
    void register_ValidInput_ReturnsSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Fitz");
        request.setUsername("fitz456");
        request.setEmail("fitz456@example.com");
        request.setPassword("securePass1");

        mockMvc.perform(post("/api/v1/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.username").value("fitz456"));
    }

    @Test
    void register_InvalidEmail_Returns400() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Fitz");
        request.setUsername("fitz789");
        request.setEmail("bad-email"); // invalid
        request.setPassword("securePass1");

        mockMvc.perform(post("/api/v1/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProfile_ValidInput_ReturnsUpdatedUser() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setName("Fitz Updated");
        updateDTO.setEmail("updated_fitz@example.com");
        updateDTO.setUsername("fitz_updated");

        mockMvc.perform(put("/api/v1/users/" + userId)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken) // ✅ Add token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Fitz Updated"))
                .andExpect(jsonPath("$.data.email").value("updated_fitz@example.com"))
                .andExpect(jsonPath("$.data.username").value("fitz_updated"));
    }

    @Test
    void updateProfile_InvalidEmail_Returns400() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("invalid-email");

        mockMvc.perform(put("/api/v1/users/" + userId)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken) // ✅ Add token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }
}
