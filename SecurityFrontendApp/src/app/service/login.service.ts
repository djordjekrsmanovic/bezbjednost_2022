import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../model/AuthenticationRequest';
import { AuthenticationResponse } from '../model/AuthenticationResponse';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = 'http://localhost:8080/' + 'api/login'; // TODO: napraviti globalnu promenljivu za http... deo
  private user = new AuthenticationResponse();

  constructor(private _http: HttpClient, private route: Router) {}

  login(request: AuthenticationRequest) {
    return this._http.post<any>(this.url, request);
  }

  loginSetUser(activeUser: AuthenticationResponse) {
    this.user = activeUser;
    console.log(this.user);
    localStorage.setItem('currentUser', JSON.stringify(this.user));
    localStorage.setItem('mail', this.user.mail);
    localStorage.setItem('logedIn', 'true')
    if (this.user.role === 'ADMIN') {
      window.location.href = '/admin';
    } else if (this.user.role === 'USER') {
      window.location.href = '/client';
    } else {
      window.location.href = '/'; 
    }
  }

  logout() {
    this.user = new AuthenticationResponse();
    localStorage.setItem('currentUser', JSON.stringify(this.user));
    localStorage.setItem('logedIn','false')
    window.location.href = '/';
  }

  getCurrentUser(): AuthenticationResponse {
    return JSON.parse(localStorage.getItem('currentUser')!);
  }

  isUserLoggedIn(): boolean {
    if(this.getCurrentUser()==null){
      return false;
    }
    return this.getCurrentUser().role == 'ADMIN' || this.getCurrentUser().role == 'USER';
  }

  getHeaders() {
    const jwt = this.getCurrentUser().jwt;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ` + jwt,
    });
    return headers;
  }

}
