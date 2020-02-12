package com.example.movieratingsservice;

import java.util.Arrays;
import com.example.movieratingsservice.controller.LoginController;
import com.example.movieratingsservice.model.AuthRequest;
import com.example.movieratingsservice.model.MyUserDetails;
import com.example.movieratingsservice.model.Role;
import com.example.movieratingsservice.model.User;
import com.example.movieratingsservice.service.ReactiveUserService;
import com.example.movieratingsservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.empty;

public class LoginControllerTests {

    private PasswordEncoder encoder;
    private AuthRequest authRequest;
    private JwtUtil jwtUtil;
    private ReactiveUserService reactiveUserService;
    private User user;
    private UserDetails userDetails;
    private WebTestClient testClient;

    @BeforeEach
    public void setUp() {
        encoder = mock(BCryptPasswordEncoder.class);
        reactiveUserService = mock(ReactiveUserService.class);
        jwtUtil = mock(JwtUtil.class);
        authRequest = new AuthRequest("user", "password");
        testClient = WebTestClient.bindToController(new LoginController(jwtUtil, reactiveUserService, encoder))
        .build();
    }

    @Test
    public void shouldReturnJWT() {
        user = new User("user", "password", Arrays.asList(new Role("ROLE_USER")));
        userDetails = new MyUserDetails(user);
        when(encoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(reactiveUserService.findByUsername(authRequest.getUsername())).thenReturn(just(userDetails));
        when(jwtUtil.createToken(userDetails)).thenReturn("JWT");
        testClient.post().uri("/login/").contentType(MediaType.APPLICATION_JSON).body(just(authRequest), AuthRequest.class)
        .exchange().expectStatus().isOk().expectBody().jsonPath("$").isNotEmpty().jsonPath("$.token").isEqualTo("JWT");
    }

    @Test
    public void shouldReturnUnathorized() {
        when(reactiveUserService.findByUsername(authRequest.getUsername())).thenReturn(empty());
        testClient.post().uri("/login/").contentType(MediaType.APPLICATION_JSON).body(just(authRequest), AuthRequest.class)
        .exchange().expectStatus().isUnauthorized();
    }
}