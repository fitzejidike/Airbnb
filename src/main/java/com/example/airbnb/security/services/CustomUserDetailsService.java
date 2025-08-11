package com.example.airbnb.security.services;

import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.exception.UserNotFoundException;
import com.example.airbnb.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        return new CustomUserDetails(user);
    }
}
