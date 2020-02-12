package com.example.movieratingsservice.model;

import java.util.Arrays;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Registration {
    private String username;
    private String password;
    private String passwordConfirm;

    public Registration() {}

    public User toUser(PasswordEncoder encoder) {
        return new User(username, encoder.encode(password), Arrays.asList(new Role("ROLE_USER")));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Registration(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}