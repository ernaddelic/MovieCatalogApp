import { TestBed, inject } from '@angular/core/testing';

import { RateMovieService } from './rate-movie.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserRating } from '../model/user-rating';

describe('RateMovieService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: RateMovieService = TestBed.get(RateMovieService);
    expect(service).toBeTruthy();
  });

  it('should rate movie', inject([RateMovieService, HttpTestingController], 
    (service: RateMovieService, controller: HttpTestingController) => {
      let mockUserRating: UserRating = {
        id: "1",
        movieId: "movie 1",
        name: "user"
      }
      service.rateMovie("1", "5").subscribe(
        (userRating: UserRating) => expect(userRating).toEqual(mockUserRating)
      )

      const req = controller.expectOne("http://localhost:8083/rate/");
      expect(req.request.method).toEqual("POST");
      req.flush(mockUserRating);

    }));
});
