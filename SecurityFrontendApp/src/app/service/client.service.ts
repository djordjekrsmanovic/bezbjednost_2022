import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  url = 'http://localhost:8080/' + 'api/certificates'; // TODO: napraviti globalnu promenljivu za http... deo

  constructor(private _http: HttpClient, private route: Router, private loginService: LoginService) { }
  
  getCertificate() {
    const headers = this.loginService.getHeaders();
    return this._http.get<any>(this.url + "/getAllUserCertificates", { headers: headers });
  }

}
