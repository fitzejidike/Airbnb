//package com.example.airbnb.security.filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.airbnb.dtos.request.LoginRequest;
//import com.example.airbnb.dtos.responses.BaseResponse;
//import com.example.airbnb.dtos.responses.LoginResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.time.Instant;
//import java.util.Collection;
//import java.util.Date;
//
//@AllArgsConstructor
//public class CustomUsernamePasswordAuthenticationFilter  extends
//        UsernamePasswordAuthenticationFilter {
//    private final AuthenticationManager authenticationManager;
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public Authentication attemptAuthentication
//            (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try {
//            //retrieve authentication credentials from request body
//            InputStream requestBodyStream = request.getInputStream();
//            //convert the json data from 1 to login request
//            LoginRequest loginRequest = mapper.readValue(requestBodyStream, LoginRequest.class);
//            String username = loginRequest.getUsername();
//            String password = loginRequest.getPassword();
//            //create authentication object that is not yet authenticated
//            Authentication authentication = new UsernamePasswordAuthenticationToken
//                    (username, password);
//            //get the now result of authentication from manager
//            Authentication authenticationResult = authenticationManager
//                    .authenticate(authentication);
//            //put the authentication result in teh security context
//            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
//            return authenticationResult;
//
//        } catch (IOException exception){
//            throw new BadCredentialsException(exception.getMessage());
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult)
//            throws IOException, ServletException {
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(generateAccessToken(authResult));
//        loginResponse.setMessage("Successful Authentication");
//
//        BaseResponse<LoginResponse> authResponse = new BaseResponse<>();
//        authResponse.setCode(HttpStatus.OK.value());
//        authResponse.setData(loginResponse);
//        authResponse.setStatus(true);
//        response.getOutputStream().write(mapper.writeValueAsBytes(authResponse));
//        response.flushBuffer();
//        chain.doFilter(request, response);
//    }
//
//    private static String generateAccessToken(Authentication authResult) {
//        return JWT.create().withIssuer("mavericks_hub").withArrayClaim("roles",
//                        getClaimsFrom(authResult.getAuthorities()))
//                .withExpiresAt(Date.from(Instant.now().plusSeconds(86400)))
//                .sign(Algorithm.HMAC512("secret"));
//    }
//
//    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities) {
//        return authorities.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setMessage(failed.getMessage());
//        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
//        baseResponse.setData(loginResponse);
//        baseResponse.setStatus(false);
//        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
//
//        response.getOutputStream().write(mapper.writeValueAsBytes(baseResponse));
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.flushBuffer();
//    }
//}
//
