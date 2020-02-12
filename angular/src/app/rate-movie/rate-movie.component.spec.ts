import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateMovieComponent } from './rate-movie.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Movie } from '../model/movie';
import { Component, ViewChild } from '@angular/core';

describe('RateMovieComponent', () => {
  let testHostComponent: TestHostComponent;
  let testHostFixture: ComponentFixture<TestHostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
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
    testHostComponent.rateMovieComponent.movie = movie;
    testHostFixture.detectChanges();
    expect(testHostFixture.nativeElement.querySelector('h5').innerText).toEqual("movie 1");
  })

  @Component({
    selector: `host-component`,
    template: `<app-rate-movie></app-rate-movie>`
  })
  class TestHostComponent {
    @ViewChild(RateMovieComponent, {static:true})
    public rateMovieComponent: RateMovieComponent;
  }
});

