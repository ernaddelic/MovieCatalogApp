import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthHttpInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): import("rxjs").Observable<import("@angular/common/http").HttpEvent<any>> {
    let authToken;
    if ((authToken = localStorage.getItem('token')) || (authToken = sessionStorage.getItem('token'))) {
      req = req.clone(
        {
          setHeaders: {
            'Authorization': `Bearer ${authToken}`
          }
        }
      )
    }
    return next.handle(req);
  }  
}
