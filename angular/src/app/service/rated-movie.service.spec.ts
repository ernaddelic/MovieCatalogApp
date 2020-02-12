import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { RatedMovieService } from './rated-movie.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { RatedMovie } from '../model/rated-movie';

describe('RatedMovieService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: RatedMovieService = TestBed.get(RatedMovieService);
    expect(service).toBeTruthy();
  });

  it('should return list of rated movies', inject([RatedMovieService,HttpTestingController],
    (service: RatedMovieService, controller: HttpTestingController) => {
      const mockRatedMovies: RatedMovie[] = [{
        id: "1",
        imgUrl: "www.testimage.com",
        title: "movie 1",
        productionCompany: "production 1",
        rating: "4"
      },
    {
      id: "2",
        imgUrl: "www.testimage.com",
        title: "movie 2",
        productionCompany: "production 2",
        rating: "4"
    }]
    spyOn(service, 'getUser').and.returnValue("user1");
    service.getRatedMovies().subscribe(
      (ratedMovies: RatedMovie[]) => {
        expect(ratedMovies).toEqual(mockRatedMovies)
      }
    )
        const req = controller.expectOne('http://localhost:8081/rate/user/user1');
        expect(req.request.method).toEqual("GET");
        req.flush(mockRatedMovies);
      }))
});
