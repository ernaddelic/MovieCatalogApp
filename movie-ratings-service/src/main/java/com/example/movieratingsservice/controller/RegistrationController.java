package com.example.movieratingsservice.controller;

import com.example.movieratingsservice.model.Registration;
import com.example.movieratingsservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> saveUser(@RequestBody Registration registration) {
        return userRepository.findByUsername(registration.getUsername()).map(user -> {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account already exists!");
        }).switchIfEmpty(userRepository.save(registration.toUser(passwordEncoder)).map(savedUser -> {
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created!"); 
        }));
    }
}