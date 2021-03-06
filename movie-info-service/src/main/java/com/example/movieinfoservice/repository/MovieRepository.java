package com.example.movieinfoservice.repository;

import com.example.movieinfoservice.model.Movie;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, String> {}