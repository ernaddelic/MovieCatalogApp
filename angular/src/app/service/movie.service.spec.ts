import { TestBed, inject } from '@angular/core/testing';
import { MovieService } from './movie.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Movie } from '../model/movie';
describe('MovieServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: MovieService = TestBed.get(MovieService);
    expect(service).toBeTruthy();
  });

  it('should return list of movies', inject(
    [MovieService, HttpTestingController], 
    (movieInfoService: MovieService, controller: HttpTestingController) => {
      const mockMovies: Movie[] = [
        {
          id: "1",
          imgUrl: "www.testimage.com",
          title: "Movie 1",
          productionCompany: "Production 1"
        },
        {
          id: "2",
          imgUrl: "www.testimage.com",
          title: "Movie 2",
          productionCompany: "Production 2"
        }
      ]
      movieInfoService.getAllMovies().subscribe(
        (movies: Movie[]) => expect(movies).toEqual(mockMovies)
      )

      const req = controller.expectOne("http://localhost:8082/movies/");
      expect(req.request.method).toEqual("GET");
      req.flush(mockMovies);
    }
  ))
});
