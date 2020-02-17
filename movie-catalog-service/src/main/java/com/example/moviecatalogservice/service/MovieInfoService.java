package com.example.moviecatalogservice.service;

import javax.annotation.PostConstruct;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    private WebClient.Builder builder;
    private RedisTemplate<String, Movie> redisTemplate;
    private HashOperations<String, String, Movie> movieCache;
    private static final String MovieCacheKey = "MovieCache";

    @PostConstruct
    public void init() {
        this.movieCache = redisTemplate.opsForHash();
    }

    public MovieInfoService(WebClient.Builder builder,
                            RedisTemplate<String, Movie> redisTemplate) {
        this.builder = builder;
        this.redisTemplate = redisTemplate;
    }

    public Mono<Movie> getMovieInfo(UserRating userRating) {
        return HystrixCommands.from(
            builder.build().get().uri("http://movie-info-service/movies/" + userRating.getMovieId())
            .retrieve().bodyToMono(Movie.class)
            .map(movie -> {
                updateCache(movie);
                return movie;
            }))
            .fallback(Mono.just(getCachedMovie(userRating)))
            .commandName("getMovieInfo")
            .toMono();
    }

    private void updateCache(Movie movie) {
        movieCache.put(MovieCacheKey, movie.getId(), movie);
    }
    private Movie getCachedMovie(UserRating userRating) {
        if (movieCache.get(MovieCacheKey, userRating.getMovieId()) != null) {
            return movieCache.get(MovieCacheKey, userRating.getMovieId());
        } else {
            return new Movie("x", "https://cdn3.vectorstock.com/i/1000x1000/00/07/not-available-flat-icon-vector-12770007.jpg", "Title Not Available", "Production Not Available");
        }
    }
}