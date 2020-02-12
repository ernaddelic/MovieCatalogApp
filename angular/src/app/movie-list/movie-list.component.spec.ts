import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MovieListComponent } from './movie-list.component';
import { RateMovieComponent } from '../rate-movie/rate-movie.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Movie } from '../model/movie';
import { Component, ViewChild } from '@angular/core';

describe('MovieListComponent', () => {
  let testHostComponent: TestHostComponent;
  let testHostFixture: ComponentFixture<TestHostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        MovieListComponent,
        RateMovieComponent,
        TestHostComponent  
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule 
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    testHostFixture = TestBed.createComponent(TestHostComponent);
    testHostComponent = testHostFixture.componentInstance;
    testHostFixture.detectChanges();
  });

  it('should show input', () => {
    let movie: Movie = {
      id: "1",
      imgUrl: "image1",
      title: "movie 1",
      productionCompany: "production 1"
    }
    testHostComponent.movieListComponent.movie = movie;
    testHostFixture.detectChanges();
    expect(testHostFixture.nativeElement.querySelector('p').innerText).toEqual("production 1");
    expect(testHostFixture.nativeElement.querySelector('img').innerText).toEqual("image1");
    expect(testHostFixture.nativeElement.querySelector('div.card>img').src).toContain('image1');
  })

  @Component({
    selector: `host-component`,
    template: `<app-movie-list></app-movie-list>`
  })
  class TestHostComponent {
    @ViewChild(MovieListComponent, {static:true})
    public movieListComponent: MovieListComponent;
  }

});
