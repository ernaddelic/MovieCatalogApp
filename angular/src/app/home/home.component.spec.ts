import { async, ComponentFixture, TestBed, inject, fakeAsync, tick, flush } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { MovieListComponent } from '../movie-list/movie-list.component';
import { RateMovieComponent } from '../rate-movie/rate-movie.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MovieService } from '../service/movie.service';
import { Movie } from '../model/movie';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let service: MovieService;

  const advance = (fixture: ComponentFixture<HomeComponent>): void => {
    tick();
    fixture.detectChanges();
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        HomeComponent,
        MovieListComponent,
        RateMovieComponent 
      ],
      imports: [
        HttpClientTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should retrieve list of movies', fakeAsync(inject([MovieService], (service: MovieService) => {
    let movies: Movie[] = [
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
    let moviesObservable: Observable<Movie[]> = of(movies);
    spyOn(service, 'getAllMovies').and.returnValue(moviesObservable);
    component.service.getAllMovies().subscribe(
        (response: Movie[]) => {
          expect(response).toEqual(movies)
      }
    )
    advance(fixture); 
  })))
});
