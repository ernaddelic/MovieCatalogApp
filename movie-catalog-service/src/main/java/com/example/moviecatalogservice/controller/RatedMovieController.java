package com.example.moviecatalogservice.controller;

import com.example.moviecatalogservice.model.RatedMovie;
import com.example.moviecatalogservice.service.MovieInfoService;
import com.example.moviecatalogservice.service.UserRatingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("rate")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RatedMovieController {

    private UserRatingService userRatingService;
    private MovieInfoService movieInfoService;
    

    public RatedMovieController(UserRatingService userRatingService,
                                MovieInfoService movieInfoService) {
        this.userRatingService = userRatingService;
        this.movieInfoService = movieInfoService;
    }

    @GetMapping("/user/{userName}")
    public Flux<RatedMovie> getRatedMovies(@PathVariable String userName) {
        return userRatingService.getUserRating(userName).flatMap(userRating -> {
            return movieInfoService.getMovieInfo(userRating).map(movie -> {
                return new RatedMovie(movie.getId(), movie.getImgUrl(), movie.getTitle(), movie.getProductionCompany(), userRating.getRating());
            });
        });
    };
}

