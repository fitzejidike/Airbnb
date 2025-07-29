package com.example.airbnb.handler;

import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.exception.GlobalException;
import com.example.airbnb.exception.InvalidEmailFoundException;
import com.example.airbnb.exception.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<String>> error(String message) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, message));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handlePasswordException(InvalidPasswordException ex) {
        return error(ex.getMessage());
    }

    @ExceptionHandler(InvalidEmailFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEmailException(InvalidEmailFoundException ex) {
        return error(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return error(ex.getMessage());
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<String>> handleGlobal(GlobalException ex) {
        return error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(errors, false, "Validation failed"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleUnknown(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return error("An unexpected error occurred");
    }
}
