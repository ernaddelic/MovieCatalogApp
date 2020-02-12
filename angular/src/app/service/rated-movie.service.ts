import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RatedMovie } from '../model/rated-movie';
import { map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RatedMovieService {

   public getUser = (): string => {
    if (localStorage.getItem('user')) {
      return localStorage.getItem('user');
    }
    return sessionStorage.getItem('user');
  }

  private api_url: string = `http://localhost:8081/rate/user/${this.getUser()}`;

  constructor(private http: HttpClient) { }

  public getRatedMovies = (): Observable<RatedMovie[]> => {
    return this.http.get(this.api_url).pipe(map(
      (movie: RatedMovie[]) => movie
    ))
  }  
}
