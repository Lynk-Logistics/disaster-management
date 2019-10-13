import { Router } from '@angular/router';
import { Component, OnInit, ɵConsole } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  FormGroup,
  FormBuilder,
  Validators
} from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  matcher: MyErrorStateMatcher;
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.registerForm = new FormGroup({
      name: new FormControl(''),
      email: new FormControl(''),
      mobileNumber: new FormControl(''),
      password: new FormControl('')
    });
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', Validators.email],
      mobileNumber: ['', [Validators.minLength(10), Validators.maxLength(10)]],
      password: ['', Validators.required]
    });

    this.matcher = new MyErrorStateMatcher();
  }

  register() {
    let params = JSON;
    params['name'] = this.registerForm.get('name').value;
    params['password'] = this.registerForm.get('password').value;
    params['email'] = this.registerForm.get('email').value;
    params['mobile'] = this.registerForm.get('mobileNumber').value;
    this.userService.register(params).subscribe(
      data => {
        this.router.navigate(['login']);
      },
      err => console.error(err),
      () => console.log('Observer got a complete notification')
    );
  }

  isRegisterDisabled() {
    return !this.registerForm.valid;
  }
}
