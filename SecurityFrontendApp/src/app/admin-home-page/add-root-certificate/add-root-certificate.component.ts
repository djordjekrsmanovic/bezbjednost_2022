import { Component, OnInit } from '@angular/core';
import { rootCertificateDTO } from 'src/app/dto-interfaces/rootCertificateDTO';
import { AdminService } from '../admin-service';

@Component({
  selector: 'app-add-root-certificate',
  templateUrl: './add-root-certificate.component.html',
  styleUrls: ['./add-root-certificate.component.css']
})
export class AddRootCertificateComponent implements OnInit {

  rootCertDTO:rootCertificateDTO= {
    adminMail:'',
    startDate:'',
    endDate:'',
    keyUsages:[],
    extendedKeyUsages:[]
  }
  constructor(private adminService:AdminService) { }

  ngOnInit(): void {
  }

  addKeyExt(extension:string){
    if(this.rootCertDTO.keyUsages.find(x => x===extension)){      
      this.rootCertDTO.keyUsages.forEach((el, index) => {if (el===extension){
        this.rootCertDTO.keyUsages.splice(index,1)        
      }})
      return;
    }   
    this.rootCertDTO.keyUsages.push(extension)
    
  }

  addKeyExtExtended(extension:string){
    if(this.rootCertDTO.extendedKeyUsages.find(x => x===extension)){      
      this.rootCertDTO.extendedKeyUsages.forEach((el, index) => {if (el===extension){
        this.rootCertDTO.extendedKeyUsages.splice(index,1)        
      }})
      return;
    }  
    this.rootCertDTO.extendedKeyUsages.push(extension)
  }

  makeRootCertificate(){   
    this.rootCertDTO.adminMail = localStorage.getItem('mail');
    this.adminService.addRootCertificate(this.rootCertDTO);
  }

}
