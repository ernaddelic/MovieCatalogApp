package com.example.movieinfoservice.configuration;

import java.util.Arrays;
import java.util.List;
import com.example.movieinfoservice.model.Movie;
import com.example.movieinfoservice.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertMovies implements CommandLineRunner {

    private MovieRepository movieRepository;
    private List<Movie> movies;

    public InsertMovies(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        movieRepository.deleteAll().subscribe(null, null, () -> {
            movies = Arrays.asList(
                new Movie("m1","https://m.media-amazon.com/images/M/MV5BYjA5YjA2YjUtMGRlNi00ZTU4LThhZmMtNDc0OTg4ZWExZjI3XkEyXkFqcGdeQXVyNjUyNjI3NzU@._V1_.jpg", 
                "Bloodshot", "Columbia Pictures / Cross Creek Pictures / Original Film"),
                new Movie("m2","https://img.over-blog-kiwi.com/3/34/77/62/20190702/ob_7329cc_my-spy.jpg", 
                "My Spy", "STX Entertainment / MWM Studios"),
                new Movie("m3","https://m.media-amazon.com/images/M/MV5BN2YyYTgxYmYtNjg3My00YzI4LWJlZWItYmZhZGEyYTYxNWY3XkEyXkFqcGdeQXVyMjAwNTYzNDg@._V1_.jpg",
                 "The Informer", "Aviron Pictures / Warner Bros. Pictures"),
                new Movie("m4","https://novosti.hr/wp-content/uploads/2019/08/No-Time-To-Die-2.jpg",
                  "No Time to Die", "Metro-Goldwyn-Mayer / United Artists Releasing / Universal Pictures / Eon Productions"),
                new Movie("m5","https://theplaylist.net/wp-content/uploads/2019/11/WAYBK_INSTA_MAIN_DOM_1936x1936_master.jpg",
                   "The Way Back", "Warner Bros. Pictures / Bron Creative"),
                new Movie("m6","https://image.tmdb.org/t/p/w500/sRhaJ6D3x6gQB0swrV0UDqMwHJu.jpg",
                    "Black Widow", "Marvel Studios"),
                new Movie("m7","https://images-na.ssl-images-amazon.com/images/I/41lcyquBPRL._AC_.jpg",
                    "Bad Boys for Life", "Columbia Pictures / Don Simpson/Jerry Bruckheimer Films"),
                new Movie("m8","https://m.media-amazon.com/images/M/MV5BMDNkODA5ZGQtODczOS00OTQxLThhMTItMjk0ZmNhMDM0YjNmXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg",
                    "Dolittle", "Universal Pictures"),
                new Movie("m9","https://c8.alamy.com/comp/2AMGB5X/hugh-grant-matthew-mcconaughey-colin-farrell-and-charlie-hunnam-in-the-gentlemen-2020-directed-by-guy-ritchie-credit-miramax-album-2AMGB5X.jpg",
                    "The Gentlemen", "STX Entertainment / Miramax")
            );
            movieRepository.saveAll(movies).subscribe();
        });
    }    
}