package com.example.airbnb.security.filter;

import com.example.airbnb.dtos.request.LoginRequest;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.dtos.responses.LoginResponse;
import com.example.airbnb.security.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private static final Logger logger = LoggerFactory.getLogger
            (CustomUsernamePasswordAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper = new ObjectMapper();

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager
                                                              authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                logger.warn("Missing username or password in login request");
                throw new BadCredentialsException("Missing username or password");
            }
            String username = loginRequest.getUsername().trim();
            if (username.isEmpty()) {
                logger.warn("Empty username in login request");
                throw new BadCredentialsException("Username cannot be empty");
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            logger.warn("Invalid login request body: {}", e.getMessage());
            throw new BadCredentialsException("Invalid request body", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String accessToken;
        String refreshToken;
        try {
            accessToken = jwtUtil.generateAccessToken(userDetails);
            refreshToken = jwtUtil.generateRefreshToken(userDetails);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to generate tokens for user {}: {}", userDetails.getUsername(), e.getMessage());
            throw new RuntimeException("Token generation failed", e);
        }

        LoginResponse loginResponse = LoginResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .message("Successful Authentication")
                .build();

        ApiResponse<LoginResponse> apiResponse = ApiResponse.success(loginResponse, "Authentication successful", HttpStatus.OK.value());

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
        logger.info("Successful login for user: {}", userDetails.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        logger.warn("Failed login attempt: {}", failed.getMessage());
        LoginResponse loginResponse = LoginResponse.builder()
                .message(failed.getMessage())
                .build();

        ApiResponse<LoginResponse> apiResponse = ApiResponse.failure(failed.getMessage(), HttpStatus.UNAUTHORIZED.value());

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}