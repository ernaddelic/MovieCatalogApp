package com.example.moviecatalogservice.controller;

import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.RatedMovie;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("rate")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RatedMovieController {

    private WebClient.Builder builder;

    public RatedMovieController(Builder builder) {
        this.builder = builder;
    }

    @GetMapping("/user/{userName}")
    public Flux<RatedMovie> getMethodName(@PathVariable String userName) {
        return builder.build().get().uri("http://movie-ratings-service/rate/user/" + userName).retrieve()
        .bodyToFlux(UserRating.class).flatMap(userRating -> {
            return builder.build().get().uri("http://movie-info-service/movies/" + userRating.getMovieId())
            .retrieve().bodyToMono(Movie.class).map(movie -> {
                return new RatedMovie(movie.getId(), movie.getImgUrl(), movie.getTitle(), movie.getProductionCompany(), userRating.getRating());
            });
        });
    };
}

