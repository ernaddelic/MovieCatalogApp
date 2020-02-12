package com.example.movieinfoservice;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.example.movieinfoservice.controller.MovieController;
import com.example.movieinfoservice.model.Movie;
import com.example.movieinfoservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;

public class MovieControllerTests {

    private MovieRepository movieRepository;
    private WebTestClient testClient;

    @BeforeEach
    public void setUp() {
        movieRepository = mock(MovieRepository.class);
        testClient = WebTestClient.bindToController(new MovieController(movieRepository))
        .build();
    }

    public Movie createMovie(Long number) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID().toString());
        movie.setTitle("Movie "  + number);
        movie.setImgUrl("Image " + number);
        movie.setProductionCompany("ProductionCompany " + number);
        return movie;        
    }

    @Test
    public void shouldReturnListOfMovies() {
        List<Movie> movies = Arrays.asList(
            createMovie(1L),
            createMovie(2L),
            createMovie(3L),
            createMovie(4L),
            createMovie(5L),
            createMovie(6L)
        );
        Flux<Movie> movieFlux = fromIterable(movies);
        when(movieRepository.findAll()).thenReturn(movieFlux);

        testClient.get().uri("/movies").exchange().expectStatus().isOk()
        .expectBody().jsonPath("$").isArray().jsonPath("$").isNotEmpty().jsonPath("$[0].id")
        .isEqualTo(movies.get(0).getId()).jsonPath("$[0].title")
        .isEqualTo("Movie 1").jsonPath("$[0].imgUrl")
        .isEqualTo("Image 1").jsonPath("$[0].productionCompany")
        .isEqualTo("ProductionCompany 1").jsonPath("$[5].id")
        .isEqualTo(movies.get(5).getId()).jsonPath("$[5].title")
        .isEqualTo("Movie 6").jsonPath("$[5].imgUrl")
        .isEqualTo("Image 6").jsonPath("$[5].productionCompany")
        .isEqualTo("ProductionCompany 6"); 
    }
    
    @Test
    public void shouldReturnMovieByItsId() {
        Movie movie = createMovie(1L);
        Mono<Movie> monoMovie = just(movie);
        when(movieRepository.findById(movie.getId())).thenReturn(monoMovie);
        testClient.get().uri("/movies/" + movie.getId()).exchange().expectStatus().isOk()
        .expectBody().jsonPath("$").isNotEmpty().jsonPath("$.id").isEqualTo(movie.getId())
        .jsonPath("$.title").isEqualTo("Movie 1").jsonPath("$.imgUrl").isEqualTo("Image 1")
        .jsonPath("$.productionCompany").isEqualTo("ProductionCompany 1");
    }
}