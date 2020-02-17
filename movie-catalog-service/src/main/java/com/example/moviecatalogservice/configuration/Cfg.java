package com.example.moviecatalogservice.configuration;

import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableCaching
public class Cfg {

    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient() {
        return WebClient.builder();
    }

    @Bean
    public RedisTemplate<String, Movie> movieRedisTemplate() {
        RedisTemplate<String, Movie> movieRedisTemplate = new RedisTemplate<>();
        movieRedisTemplate.setConnectionFactory(this.redisConnectionFactory);
        movieRedisTemplate.setEnableTransactionSupport(true);
        return movieRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, UserRating> userRedisTemplate() {
        RedisTemplate<String, UserRating> userRedisTemplate = new RedisTemplate<>();
        userRedisTemplate.setConnectionFactory(this.redisConnectionFactory);
        userRedisTemplate.setEnableTransactionSupport(true);
        return userRedisTemplate;
    }
    
    public Cfg(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }
}