import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Movie } from '../model/movie';
import { RateMovieService } from '../service/rate-movie.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rate-movie',
  templateUrl: './rate-movie.component.html',
  styleUrls: ['./rate-movie.component.css']
})
export class RateMovieComponent implements OnInit {
  @Input() movie: Movie;
  public rating: string = "";

  constructor(private service: RateMovieService,
    private router: Router) {}

  ngOnInit() {}

  onChange = (entry): void => {
    this.rating = entry;
  }

  public rateMovie = (): void => {
    this.service.rateMovie(this.movie.id, this.rating).subscribe(
      data => this.router.navigate(['/home'])
    )
  }
  public isDisabled(): boolean {
    if (localStorage.getItem('token')) {
      return false;
    } else if (sessionStorage.getItem('token')) {
      return false;
    } else {
      return true;
    }
  }
}