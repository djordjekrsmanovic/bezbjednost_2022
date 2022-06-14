import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import { rootCertificateDTO } from '../dto-interfaces/rootCertificateDTO';
import { CertificateDTO } from '../model/CertificateDTO';
import { revokeCertificateDTO } from '../dto-interfaces/revokeCertificateDTO';
import { LoginService } from '../service/login.service';
import { server } from '../app-global';
import { LoadCertificatesForSigningDto } from '../model/LoadCertificateForSigningDto';
import { CreateCertificateDto } from '../model/CreateCertificateDto';


@Injectable({
    providedIn: 'root',
  })

  export class AdminService {
    constructor(private http: HttpClient,private loginService:LoginService) {}

    makeCertificate(){
      
    }

    revokeCertificate(dto:revokeCertificateDTO){
      const headers = this.loginService.getHeaders(); 
      const body=JSON.stringify(dto) 
        this.http.post(server + 'api/certificates/revoke-certificate',body,{'headers': headers}).subscribe(data=>console.log(data)
        );
    }

    downloadCertificate() : Observable<Blob> {
      return this.http.get(server + 'downloadCertificate', {responseType: 'blob'});
  }
  
    url = server + 'api/certificates';
    downloadCertificateWeb(serialNumber: string): Observable<Blob> {
      return this.http.get(this.url + "/downloadCertificate/" + serialNumber, {
        responseType: 'blob'
      });
    }

    addRootCertificate(dto :rootCertificateDTO){
      const headers = this.loginService.getHeaders();  
      const body=JSON.stringify(dto) 
      this.http.post(server + 'api/certificates/add-root-certificate',body,{'headers': headers}).subscribe(data=>console.log(data),error=> alert(error.error));
    }

    getAllCertificates(): Observable<CertificateDTO[]> {
      const headers = this.loginService.getHeaders(); 
      return this.http.get<CertificateDTO[]>(server + 'api/certificates/getAllCertificates',{headers:headers});
    }

    getCertificateForSigning(dates:LoadCertificatesForSigningDto):Observable<CertificateDTO[]>{
      const headers = this.loginService.getHeaders(); 
      const body=JSON.stringify(dates) 
      return this.http.post<CertificateDTO[]>(server+'api/certificates/get-certificate-for-signing',body,{headers:headers});
    }

    getUserCertificateForSigning(dates:LoadCertificatesForSigningDto):Observable<CertificateDTO[]>{
      const headers = this.loginService.getHeaders(); 
      const body=JSON.stringify(dates) 
      return this.http.post<CertificateDTO[]>(server+'api/certificates/get-user-certificate-for-signing',body,{headers:headers});
    }

    addCertificate(dto:CreateCertificateDto){
      const headers = this.loginService.getHeaders(); 
      const body=JSON.stringify(dto) 
      return this.http.post<CertificateDTO[]>(server+'api/certificates/add-certificate',body,{headers:headers}).subscribe(data => console.log(data),error=> alert(error.error));
      
    }

  }