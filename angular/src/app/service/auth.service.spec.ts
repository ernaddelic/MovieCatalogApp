import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { AuthService } from './auth.service';
import { AuthRequest } from '../model/auth-request';
import { AuthResponse } from '../model/auth-response';

describe('AuthService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
  }));

  it('should be created', () => {
    const service: AuthService = TestBed.get(AuthService);
    expect(service).toBeTruthy();
  });
  it('should return jwt for correct credentials', inject([AuthService, HttpTestingController], 
    (service: AuthService, controller: HttpTestingController) => {
      let authRequest: AuthRequest = {
        username: 'user',
        password: 'password'
      }
      let authResponse: AuthResponse = {
        token: "JWT"
      }
      service.login(authRequest).subscribe((data:string) => {
        expect(data).toEqual("JWT");
      })
      const req = controller.expectOne("http://localhost:8083/login/");
      expect(req.request.method).toEqual('POST');
      req.flush(authResponse);
}))
}); 
