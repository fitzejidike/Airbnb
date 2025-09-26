package com.example.airbnb.security.provider;

import com.example.airbnb.data.model.Role;
import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.UserRepository;
import com.example.airbnb.security.model.CustomUserDetails;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class CustomOAuth2AuthenticationProvider implements AuthenticationProvider {

    // Replace with your Google OAuth2 Client ID
    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com";

    private final UserRepository userRepository;  // ✅ Inject repo so we can fetch/create user

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idTokenString = authentication.getCredentials().toString();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // ✅ Step 1: Check DB if user exists
                User user = userRepository.findByEmail(email)
                        .orElseGet(() -> {
                            // Auto-register new user if not found
                            User newUser = User.builder()
                                    .email(email)
                                    .name(name)
                                    .username(email.split("@")[0]) // simple default username
                                    .role(Role.GUEST)
                                    .build();
                            return userRepository.save(newUser);
                        });

                // ✅ Step 2: Wrap into CustomUserDetails
                CustomUserDetails userDetails = new CustomUserDetails(user);

                // ✅ Step 3: Return authenticated token with proper principal
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            } else {
                throw new BadCredentialsException("Invalid Google ID Token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new BadCredentialsException("Error verifying Google ID Token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
