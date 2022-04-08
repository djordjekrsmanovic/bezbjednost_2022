import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { AuthenticationResponse } from '../../model/AuthenticationResponse';
import { LoginService } from '../../service/login.service';


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

  isUserLoggedIn(): boolean{
    return this.loginService.isUserLoggedIn();
  }

  successfulLogin(data: AuthenticationResponse) {
    this.errorMessage = '';
    console.log(data);
    this.loginService.loginSetUser(data);
  }

}
