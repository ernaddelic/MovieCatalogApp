import { Component, OnInit } from '@angular/core';
import { MovieService } from '../service/movie.service';
import { Movie } from '../model/movie';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
   movies: Movie[];

  public updateMovies = (movies: Movie[]) => {
    this.movies = movies;
  }

  constructor(public service: MovieService) {
    
   }

  ngOnInit() {
    this.service.getAllMovies().subscribe(
      (movies: Movie[]) => {
        this.updateMovies(movies)
      }
    )
    
  }
}
