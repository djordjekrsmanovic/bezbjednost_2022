import { Component, Input, OnInit } from '@angular/core';
import { CertificateDTO } from 'src/app/model/CertificateDTO';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {
  @Input() certificate!: CertificateDTO;

  constructor() { }

  ngOnInit(): void {
  }

}
