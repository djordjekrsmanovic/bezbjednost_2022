import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { UserDto } from '../model/UserDto';
import { server } from '../app-global';
import { Observable } from 'rxjs';
import { CertificateDTO } from '../model/CertificateDTO';

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

  getUsers(){
    const headers=this.loginService.getHeaders();
    return this._http.get<UserDto[]>(server+'api/clients/get-users',{headers: headers});
  }

  getUsersWithoutPrincipal(){
    const headers=this.loginService.getHeaders();
    return this._http.get<UserDto[]>(server+'api/clients/get-users-without-principal',{headers: headers});
  }

  downloadCertificate(serialNumber: string) : Observable<CertificateDTO> {
    const headers = this.loginService.getHeaders();
    return this._http.get<any>(this.url + "/downloadCertificate/" + serialNumber, { headers: headers });
  }
}
