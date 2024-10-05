package com.example.airbnb.security.utils;

import java.util.List;

public class SecurityUtils {
    private SecurityUtils() {}
        public static final List<String> PUBLIC_ENDPOINTS = List.of("/api/vi/auth");
        public static final String JWT_PREFIX = "Bearer ";


}
