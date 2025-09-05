//package com.example.airbnb.security.config;
//
//import com.example.airbnb.security.filter.CustomUsernamePasswordAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//    private final AuthenticationManager authenticationManager;
//
//    public SecurityConfig(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        var authenticationFilter =
//                new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
//        authenticationFilter.setFilterProcessesUrl("/api/v1/auth");
//        return http.csrf(c->c.disable())
//                .cors(c->c.disable())
//                .authorizeHttpRequests(c->c.anyRequest().permitAll())
//                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
//                .build();
//    }
////   a
//}
