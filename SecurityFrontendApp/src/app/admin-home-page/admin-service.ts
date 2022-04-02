import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import { rootCertificateDTO } from '../dto-interfaces/rootCertificateDTO';
import { CertificateDTO } from '../model/CertificateDTO';
import { revokeCertificateDTO } from '../dto-interfaces/revokeCertificateDTO';

@Injectable({
    providedIn: 'root',
  })

  export class AdminService {
    constructor(private http: HttpClient) {}

    makeCertificate(){
      
    }

    revokeCertificate(dto:revokeCertificateDTO){
      const headers={'content-type':'application/json'};  
      const body=JSON.stringify(dto) 
        this.http.post('http://localhost:8080/api/certificates/revoke-certificate',body,{'headers': headers}).subscribe(data=>console.log(data)
        );
    }

    downloadCertificate() : Observable<Blob> {
      return this.http.get('http://localhost:8080/downloadCertificate', {responseType: 'blob'});
    }

    addRootCertificate(dto :rootCertificateDTO){
      const headers={'content-type':'application/json'};  
      const body=JSON.stringify(dto) 
      this.http.post('http://localhost:8080/api/certificates/add-root-certificate',body,{'headers': headers}).subscribe(data=>console.log(data)
      );
    }

    getAllCertificates(): Observable<CertificateDTO[]> {
      return this.http.get<CertificateDTO[]>('http://localhost:8080/api/certificates/getAllCertificates');
    }

  }