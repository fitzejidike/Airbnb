package com.example.airbnb.security.config;

import com.example.airbnb.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.airbnb.security.filter.JwtAuthenticationFilter;
import com.example.airbnb.security.services.CustomUserDetailsService;
import com.example.airbnb.security.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager, jwtUtil);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        return http
                .csrf(csrf -> csrf.disable()) // Safe for JWT-based APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // Allow login and OAuth endpoints
                        .requestMatchers("/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/properties/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/properties").hasRole("HOST")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/properties/**").hasRole("HOST")

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Require auth for all other endpoints
                )
                .addFilter(authenticationFilter)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://your-frontend.com", "http://localhost:3000")); // Add trusted origins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache preflight requests for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}