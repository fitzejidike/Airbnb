package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.exception.InvalidEmailFoundException;
import com.example.airbnb.exception.InvalidPasswordException;

import java.util.regex.Pattern;

public class InputValidators {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern ALPHANUMERIC_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]*$");

    public static void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailFoundException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {
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
}
