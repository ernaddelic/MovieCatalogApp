package com.example.movieratingsservice.configuration;

import com.example.movieratingsservice.service.ReactiveUserService;
import com.example.movieratingsservice.util.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationMenager implements ReactiveAuthenticationManager {

    private JwtUtil jwtUtil;
    private ReactiveUserService reactiveUserService;

    public AuthenticationMenager(JwtUtil jwtUtil, ReactiveUserService reactiveUserService) {
        this.jwtUtil = jwtUtil;
        this.reactiveUserService = reactiveUserService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUserNameFromToken(authToken);
        if (username != null && jwtUtil.validateToken(authToken)) {
            return reactiveUserService.findByUsername(username).map(userDetails -> {
                return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            });
        } else {
            return Mono.empty();
        }
    }   
}