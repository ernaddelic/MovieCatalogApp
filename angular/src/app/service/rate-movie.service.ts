import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRating } from '../model/user-rating';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RateMovieService {
  private api_url: string = "http://localhost:8083/rate/";

  constructor(private http: HttpClient) { }

  public rateMovie = (id:string, rating: string): Observable<UserRating> => {
    return this.http.post(this.api_url, {
      id: id,
      rating: rating
    }).pipe(map((data: UserRating) => data));
   }
}
