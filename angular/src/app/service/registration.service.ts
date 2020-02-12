import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Registration } from '../model/registration';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private api_url: string = "http://localhost:8083/register/";

  constructor(private http: HttpClient) { }

  public register = (registration:Registration): Observable<string> => {
    return this.http.post(this.api_url, registration, {responseType: 'text'});
  }
}
