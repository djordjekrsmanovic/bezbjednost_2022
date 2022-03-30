import { Component, OnInit } from '@angular/core';
import { AuthenticationRequest } from './AuthenticationRequest';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  authenticationRequest: AuthenticationRequest;

  mail: string = "";
  password: string = "";

  constructor() {
    this.authenticationRequest = new AuthenticationRequest("","");
  }

  ngOnInit(): void {
  }

}
