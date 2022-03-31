import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root',
  })

  export class AdminService {
    constructor(private http: HttpClient) {}

    makeCertificate(){
      
    }

    revokeCertificate(serialNum:string){
      const headers={'content-type':'application/json'};  
      const body=JSON.stringify(serialNum) 
        this.http.post('http://localhost:8080/revoke',body,{'headers': headers}).subscribe(data=>console.log(data)
        );
    }

    downloadCertificate() : Observable<Blob> {
      return this.http.get('http://localhost:8080/downloadCertificate', {responseType: 'blob'});
    }

  }