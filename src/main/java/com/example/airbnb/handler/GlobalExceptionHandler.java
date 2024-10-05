package com.example.airbnb.handler;

import com.example.airbnb.exception.GlobalException;
import com.example.airbnb.exception.InvalidEmailFoundException;
import com.example.airbnb.exception.InvalidPasswordException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handlePasswordException
            (InvalidPasswordException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
                "message", exception.getMessage(),"success",false));
    }
    @ExceptionHandler(InvalidEmailFoundException.class)
    public ResponseEntity<?>handleEmailException(InvalidEmailFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(Map.of("message", exception.getMessage(),
                "success",false));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException
            (UsernameNotFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("message",exception.getMessage(),"success",false ));
    }
    @ExceptionHandler
    public ResponseEntity<?>handleGlobalException(GlobalException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of("message", exception.getMessage(),
                        "success",false));
    }
}
