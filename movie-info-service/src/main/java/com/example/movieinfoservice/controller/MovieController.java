package com.example.movieinfoservice.controller;

import com.example.movieinfoservice.model.Movie;
import com.example.movieinfoservice.repository.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public Flux<Movie> getMethodName() {
        return movieRepository.findAll();
    }
    
    @GetMapping("/{movieId}")
    public Mono<Movie> getMovieById(@PathVariable String movieId) {
        return movieRepository.findById(movieId);
    }
}