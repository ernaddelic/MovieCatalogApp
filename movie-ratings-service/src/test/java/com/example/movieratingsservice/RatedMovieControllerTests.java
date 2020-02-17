package com.example.movieratingsservice;

import com.example.movieratingsservice.controller.RatedMovieController;
import com.example.movieratingsservice.model.RatedMovie;
import com.example.movieratingsservice.repository.RatedMovieRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

public class RatedMovieControllerTests {

    private RatedMovieRepository ratedMovieRepository;

    @Test
    public void shouldFindUserByUsername() {
        ratedMovieRepository = mock(RatedMovieRepository.class);
        RatedMovie ratedMovie = new RatedMovie("1", "user", "5");
        when(ratedMovieRepository.findByUsername("user")).thenReturn(Flux.just(ratedMovie));
        WebTestClient testClient = WebTestClient.bindToController(new RatedMovieController(ratedMovieRepository)).build();
        testClient.get().uri("/rate/user/user").exchange().expectBody().jsonPath("$").isNotEmpty()
        .jsonPath("$[0].movieId").isEqualTo("1").jsonPath("$[0].username").isEqualTo("user")
        .jsonPath("$[0].rating").isEqualTo("5");
    }
}