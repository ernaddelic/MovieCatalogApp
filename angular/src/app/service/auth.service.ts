import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../model/auth-request';
import { Observable } from 'rxjs';
import { AuthResponse } from '../model/auth-response';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api_url: string = "http://localhost:8083/login/";

  constructor(private http: HttpClient) { }

  public login = (authRequest: AuthRequest): Observable<string> => {
    return this.http.post(this.api_url, authRequest).pipe(
      map((authResponse: AuthResponse) => authResponse.token)
    )
  } 
}
