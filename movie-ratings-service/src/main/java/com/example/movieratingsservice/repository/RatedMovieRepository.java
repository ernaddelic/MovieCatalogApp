package com.example.movieratingsservice.repository;

import com.example.movieratingsservice.model.RatedMovie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RatedMovieRepository extends ReactiveCrudRepository<RatedMovie, String> {
    public Flux<RatedMovie> findByUsername(String username);
}