import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  form: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService,
    private snackBar: MatSnackBar,
  ) {
    this.form = this.fb.group({
      username : [null,  Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z0-9]+')
      ])],
      password: [null,  Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!?]+')
      ])]
    });
  }

  ngOnInit() {
    if (this.authenticationService.isLoggedIn()) {
      this.router.navigate(['home']);
    }
  }

  submit() {
    const auth: any = {};
    const jwt: JwtHelperService = new JwtHelperService();
    auth.username = this.form.value.username;
    auth.password = this.form.value.password;
    console.log(auth.username);
    console.log(auth.password);


    this.authenticationService.login(auth).subscribe(
      result => {
        this.errorMessage = '';
        this.snackBar.open('Logged In successfully');
        localStorage.setItem('user', JSON.stringify(result));
        console.log(JSON.stringify(localStorage.getItem('user')));
        this.router.navigate(['add-certificate-form']);
      },
      error => {
        // this.toastr.error('Incorrect username or password!');
        this.snackBar.open('Incorrect username or password!');
        this.errorMessage = 'Incorrect username or password!';
      }
    );
  }

}
