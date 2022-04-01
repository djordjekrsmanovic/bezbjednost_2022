import { Component, OnInit } from '@angular/core';
import { AdminService } from './admin-service';
import { saveAs } from 'file-saver'

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.css']
})
export class AdminHomePageComponent implements OnInit {

  constructor( private adminService : AdminService) { }

  rootCertTab:boolean=true;
  otherCertTab:boolean=false;
  allCertsTab:boolean=false;
  toggleDetailWin:boolean=false;
  filtersCard:boolean=false;
  phase=1;
  user:any;
  issuer:any;
  expirationDate:any;

  selectOtherCertTab(){
    this.otherCertTab=true;
    this.rootCertTab=false;
    this.allCertsTab=false;
  }
  selectRootCertTab(){
    this.otherCertTab=false;
    this.rootCertTab=true;
    this.allCertsTab=false;
  }
  selectAllCertsTab(){
    this.otherCertTab=false;
    this.rootCertTab=false;
    this.allCertsTab=true;
  }

  toggleFilters(){
    if(this.filtersCard===false)
      this.filtersCard=true;
    else
     this.filtersCard=false;
  }

  toggleModal(){
    if(this.toggleDetailWin===false)
      this.toggleDetailWin=true;
    else
     this.toggleDetailWin=false;
  }

  nextPhase(){
    if(this.user==null && this.phase==1){
      alert('You must select user first.')
      return;
    }
    if(this.issuer==null && this.phase==2){
      alert('You must select issuer first.')
      return;
    }
    if(this.expirationDate==null && this.phase==3){
      alert('You must determine expiration date before proceeding.')
      return;
    }    
    this.phase+=1;
    if(this.phase==4){
      this.adminService.makeCertificate();
      this.phase=1;
    }
  }
  
  selectUser(){

  }

  returnToPreviousPhase(){
    this.phase-=1;
  }

  downloadCertificate(){
    this.adminService.downloadCertificate().subscribe(blob =>saveAs(blob, 'certificate.cer'))
  }


  ngOnInit(): void {

  }

}
