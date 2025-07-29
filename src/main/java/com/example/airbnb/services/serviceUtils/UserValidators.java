package com.example.airbnb.services.serviceUtils;

import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.exception.UserAlreadyExistsException;

public class UserValidators {
    public static void checkIfUserExists(UserRepository repo, String email, String username) {
        repo.findByEmail(email).ifPresent(u -> {
            throw new  UserAlreadyExistsException("A user with this email already exists");
        });

        repo.findByUsername(username).ifPresent(u -> {
            throw new UserAlreadyExistsException("A user with this username already exists");
        });
    }
}
