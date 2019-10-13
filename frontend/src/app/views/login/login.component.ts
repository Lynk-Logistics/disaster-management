import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  FormGroup,
  FormBuilder,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  matcher: MyErrorStateMatcher;
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    });
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.matcher = new MyErrorStateMatcher();
  }

  login() {
    let params = JSON;
    params['userIdentifier'] = this.loginForm.get('username').value;
    params['password'] = this.loginForm.get('password').value;
    this.userService.login(params).subscribe(
      (data: any) => {
        if (data['message']) {
          localStorage.setItem('token', `Bearer ${data.message}`);
          localStorage.setItem('initial', data['responseObject'].name.charAt(0));
          this.router.navigate(['/homepage']);
        }
      },
      err => console.error(err),
      () => console.log('Observer got a complete notification')
    );
  }

  isLoginDisabled() {
    return !this.loginForm.valid;
  }
}
