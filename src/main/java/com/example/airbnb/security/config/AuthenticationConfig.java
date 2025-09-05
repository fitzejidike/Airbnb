package com.example.airbnb.security.config;

import com.example.airbnb.security.provider.CustomAuthenticationProvider;
import com.example.airbnb.security.provider.CustomOAuth2AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomOAuth2AuthenticationProvider customOAuth2AuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(
                customAuthenticationProvider,
                customOAuth2AuthenticationProvider
        ));
    }
}
