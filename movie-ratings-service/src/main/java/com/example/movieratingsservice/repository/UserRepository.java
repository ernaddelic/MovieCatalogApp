package com.example.movieratingsservice.repository;

import com.example.movieratingsservice.model.User;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {
    public Mono<User> findByUsername(String username);
}