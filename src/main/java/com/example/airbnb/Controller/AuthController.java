package com.example.airbnb.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return "Logged in as: " + auth.getName();
    }

    @PostMapping("/oauth2")
    public String oauthLogin(@RequestParam String token) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(null, token)
        );
        return "OAuth2 login as: " + auth.getName();
    }
}
