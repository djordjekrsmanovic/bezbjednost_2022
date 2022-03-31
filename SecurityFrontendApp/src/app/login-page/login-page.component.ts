import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from './AuthenticationRequest';
import { AuthenticationResponse } from './AuthenticationResponse';
import { LoginService } from './login.service';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {

  authenticationRequest: AuthenticationRequest;
  errorMessage: string = "";

  constructor(private loginService: LoginService, private route: Router) {
    this.authenticationRequest = new AuthenticationRequest("","");
  }

  ngOnInit(): void {
  }

  login() {
    if (this.authenticationRequest.mail == '' || this.authenticationRequest.password == '') {
      this.errorMessage = 'Email or password missing.';
    } else {
      this.loginService.login(this.authenticationRequest).subscribe(
        (data) => this.successfulLogin(data),
        (res) => (this.errorMessage = 'Invalid email or password.')
      );
    }
  }

  successfulLogin(data: AuthenticationResponse) {
    this.errorMessage = '';
    console.log(data);
    this.loginService.loginSetUser(data);
  }

}
