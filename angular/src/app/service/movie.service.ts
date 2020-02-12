import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../model/movie';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private api_url: string = 'http://localhost:8082/movies/';

  constructor(private http: HttpClient) { }

  public getAllMovies = (): Observable<Movie[]> => {
    return this.http.get(this.api_url).pipe(
      map((movies: Movie[]) => {
        return movies;
      })
    )
  }
}
