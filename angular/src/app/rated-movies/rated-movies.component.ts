import { Component, OnInit } from '@angular/core';
import { RatedMovie } from '../model/rated-movie';
import { RatedMovieService } from '../service/rated-movie.service';

@Component({
  selector: 'app-rated-movies',
  templateUrl: './rated-movies.component.html',
  styleUrls: ['./rated-movies.component.css']
})
export class RatedMoviesComponent implements OnInit {
  ratedMovies: RatedMovie[];

  constructor(public service: RatedMovieService) { }

  ngOnInit() {
    this.service.getRatedMovies().subscribe(
      (ratedMovies: RatedMovie[]) => {
      this.ratedMovies = ratedMovies
      }
    )
  }
  isChecked = (input: number, rating: number): boolean => {
    if (input <= rating) {
      return true;
    }
    return false;
  }
}
