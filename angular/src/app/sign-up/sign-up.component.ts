import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { RegistrationService } from '../service/registration.service';
import { Router } from '@angular/router';

export function matchValue(control: AbstractControl): {[key:string]: boolean}  {
  if (control.get('password').value !== control.get('confirmPassword').value) {
    return {notMatch: true}
  }
  return null;
}

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  public formGroup: FormGroup;
  public errorMsg: string = '';

  constructor(private fb: FormBuilder,
    private service: RegistrationService,
    private router: Router) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.compose([
        Validators.required, Validators.minLength(8)
      ])],
      'confirmPassword' : ['', Validators.compose([
        Validators.required, Validators.minLength(8)
      ])]
    }, {validator: matchValue})
  }

  onSubmit = (): void => {
    this.service.register(this.formGroup.value).subscribe(
      () => {}, 
      () => {
        this.errorMsg = 'Account already exists'
        setTimeout(() => this.errorMsg = '', 3000);
      },
      () => {
        this.router.navigate(['/sign-in']);
        alert("Account created!")
      }
    )   
}
}
