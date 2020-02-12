import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  public formGroup: FormGroup;
  public rememberMe: boolean = false;
  public errorMsg: string = '';

  constructor(private fb: FormBuilder,
    private service: AuthService,
    private router: Router) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.compose([
        Validators.required, Validators.minLength(8)
      ])]
    })
  }

  onSubmit = (): void => {
    this.service.login(this.formGroup.value).subscribe(
      (token: string) => {
        if (this.rememberMe) {
          localStorage.setItem("token", token);
          localStorage.setItem("user", this.formGroup.value.username);
        } else {
          sessionStorage.setItem("token", token);
          sessionStorage.setItem("user", this.formGroup.value.username);
          localStorage.removeItem("token");
          localStorage.removeItem("user");
        }
        this.router.navigate(['/home']);
      },
      () => {
        this.errorMsg = 'Invalid username or password'
        setTimeout(() => this.errorMsg = '', 3000);
      }
    )
  }
}
