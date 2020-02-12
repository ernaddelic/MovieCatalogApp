package com.example.movieratingsservice.controller;

import java.security.Principal;
import com.example.movieratingsservice.model.Movie;
import com.example.movieratingsservice.model.RatedMovie;
import com.example.movieratingsservice.repository.RatedMovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rate")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RatedMovieController {

    private RatedMovieRepository ratedMovieRepository;

    public RatedMovieController(RatedMovieRepository ratedMovieRepository) {
        this.ratedMovieRepository = ratedMovieRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<RatedMovie>> postMethodName(@RequestBody Movie movie, Principal principal) {
        RatedMovie ratedMovie = new RatedMovie(movie.getId(), principal.getName(), movie.getRating());
        return ratedMovieRepository.save(ratedMovie).map((rMovie) -> {
            return ResponseEntity.status(HttpStatus.CREATED).body(rMovie);
        });
    }
    
    @GetMapping("/user/{username}")
    public Flux<RatedMovie> getMovieByUserName(@PathVariable String username) {
        return ratedMovieRepository.findByUsername(username);
    }
}