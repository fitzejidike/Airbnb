//package com.example.airbnb.security.provider;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GoogleAuthenticationProvider  implements AuthenticationProvider {
////
//    private final OAuth2LoginAuthenticationProvider oAuth2LoginProvider;
//
//    @Autowired
//    public GoogleAuthenticationProvider(OAuth2LoginAuthenticationProvider oAuth2LoginProvider) {
//        this.oAuth2LoginProvider = oAuth2LoginProvider;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//}
