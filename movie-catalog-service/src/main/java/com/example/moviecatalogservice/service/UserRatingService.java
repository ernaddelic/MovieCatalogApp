package com.example.moviecatalogservice.service;

import javax.annotation.PostConstruct;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class UserRatingService {

    private WebClient.Builder builder;
    private RedisTemplate<String, UserRating> redisTemplate;
    private HashOperations<String, String, UserRating> userRatingCache;
    private static final String UserRatingCacheKey = "UserRatingCache";

    @PostConstruct
    public void init() {
        this.userRatingCache = redisTemplate.opsForHash();
    }

    public UserRatingService(WebClient.Builder builder,
                            RedisTemplate<String, UserRating> redisTemplate ) {
        this.builder = builder;
        this.redisTemplate = redisTemplate;
    }

    public Flux<UserRating> getUserRating(String username) {
        return HystrixCommands.from(builder.build().get().uri("http://movie-ratings-service/rate/user/" + username).retrieve()
        .bodyToFlux(UserRating.class)
        .doOnNext(userRating -> updateCache(userRating)
        ))
        .fallback(Flux.just(userRatingCache.get(UserRatingCacheKey, username))) 
        .commandName("getUserRating")
        .toFlux();
    }
    
    private void updateCache(UserRating userRating) {
        userRatingCache.put(UserRatingCacheKey, userRating.getUsername(), userRating);
    }
}