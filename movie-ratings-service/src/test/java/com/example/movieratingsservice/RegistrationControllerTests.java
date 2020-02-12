package com.example.movieratingsservice;

import static org.mockito.ArgumentMatchers.any;
import com.example.movieratingsservice.controller.RegistrationController;
import com.example.movieratingsservice.model.Registration;
import com.example.movieratingsservice.model.User;
import com.example.movieratingsservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.empty;

public class RegistrationControllerTests {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Registration registration;
    private WebTestClient testClient;
    private User user;

    @BeforeEach
    public void setUp() {
        registration = new Registration("username", "password", "passwordConfirm");
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        when(passwordEncoder.encode(registration.getPassword())).thenReturn("encodedPassword");
        user = registration.toUser(passwordEncoder);
        when(userRepository.save(any(User.class))).thenReturn(just(user));
        testClient = WebTestClient.bindToController(new RegistrationController(userRepository, passwordEncoder)).build();
    }

    @Test
    public void shouldReturnHttpCreated() {
        when(userRepository.findByUsername(registration.getUsername())).thenReturn(empty());
        testClient.post().uri("/register").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .body(just(registration), Registration.class).exchange().expectStatus().isCreated()
        .expectBody().equals("Account created!");
    }

    @Test
    public void shouldReturnHttpForbidden() {
        when(userRepository.findByUsername(registration.getUsername())).thenReturn(just(user));
        testClient.post().uri("/register").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .body(just(registration), Registration.class).exchange().expectStatus().isForbidden()
        .expectBody().equals("Account already exists!");
    }
}
