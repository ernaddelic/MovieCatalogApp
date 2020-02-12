import { async, ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { RatedMoviesComponent } from './rated-movies.component';
import { HttpClientModule } from '@angular/common/http';
import { RatedMovieService } from '../service/rated-movie.service';
import { RatedMovie } from '../model/rated-movie';
import { Observable, of } from 'rxjs';

describe('RatedMoviesComponent', () => {
  let component: RatedMoviesComponent;
  let fixture: ComponentFixture<RatedMoviesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatedMoviesComponent ],
      imports: [HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatedMoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return rated movies', fakeAsync(inject([RatedMovieService], (service: RatedMovieService) => {
    const ratedMovies: RatedMovie[] = [
      {
        id: "m1",
        title: "movie 1",
        imgUrl: "www.testimage.com",
        productionCompany: "production 1",
        rating: "5"
      },
      {
        id: "m2",
        title: "movie 2",
        imgUrl: "www.testimage.com",
        productionCompany: "production 2",
        rating: "3"
      }
    ]
    const ratedMoviesStream: Observable<RatedMovie[]> = of(ratedMovies);
    spyOn(service,'getRatedMovies').and.returnValue(ratedMoviesStream);
    let res: RatedMovie[];
    service.getRatedMovies().subscribe(
      (ratedMovies: RatedMovie[]) => {
        res = ratedMovies;
      }
    )
    tick();
    fixture.detectChanges();
    expect(res).toEqual(ratedMovies);
  })))
});
