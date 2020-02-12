package com.example.movieratingsservice.service;

import com.example.movieratingsservice.model.MyUserDetails;
import com.example.movieratingsservice.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserService implements ReactiveUserDetailsService {

    private UserRepository userRepository;

    public ReactiveUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> new MyUserDetails(user));
    }  
}