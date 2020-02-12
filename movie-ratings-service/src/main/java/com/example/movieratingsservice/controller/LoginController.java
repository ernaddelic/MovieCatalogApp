package com.example.movieratingsservice.controller;

import com.example.movieratingsservice.model.AuthRequest;
import com.example.movieratingsservice.model.AuthResponse;
import com.example.movieratingsservice.service.ReactiveUserService;
import com.example.movieratingsservice.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private ReactiveUserService reactiveUserService;

    public LoginController(JwtUtil jwtUtil, 
                           ReactiveUserService reactiveUserService,
                           PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.reactiveUserService = reactiveUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public Mono<ResponseEntity<?>> getJWT(@RequestBody AuthRequest authRequest) {
        return reactiveUserService.findByUsername(authRequest.getUsername()).map((user) -> {
                if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                    return ResponseEntity.ok(new AuthResponse(jwtUtil.createToken(user)));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
             }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());   
    } 
}