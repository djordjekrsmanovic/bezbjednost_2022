import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateDTO } from 'src/app/model/CertificateDTO';
import { ClientService } from 'src/app/service/client.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-client-home-page',
  templateUrl: './client-home-page.component.html',
  styleUrls: ['./client-home-page.component.css']
})
export class ClientHomePageComponent implements OnInit {

  certificates!: CertificateDTO[];

  selectedCertificate!: CertificateDTO;
  selectedCertificateBool: boolean = false;

  constructor(private clientService: ClientService, private route: Router) { }

  ngOnInit(): void {
    this.clientService.getCertificate().subscribe((data) => {
      this.certificates = data;
      console.log(this.certificates);
      
    });
  }

  showMoreInfo(cert: CertificateDTO): void {
    this.selectedCertificate = cert;
    this.selectedCertificateBool = true;
  }

  closeShowMoreInfo(): void {
    this.selectedCertificateBool = false;
  }
  
  /*
  download(): void {
    this.clientService.downloadCertificate(this.selectedCertificate.serialNumber).subscribe(() =>  {});;
    
    this.selectedCertificateBool = false;
  }
  */

  download(): void {
    this.clientService
      .downloadCertificateWeb(this.selectedCertificate.serialNumber)
      .subscribe(blob => saveAs(blob, "Certificate"+this.selectedCertificate.serialNumber+".cer"));
  }
}
