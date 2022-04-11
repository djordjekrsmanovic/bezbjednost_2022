import { Component, OnInit } from '@angular/core';
import { AdminService } from './admin-service';
import { saveAs } from 'file-saver'
import { rootCertificateDTO } from '../dto-interfaces/rootCertificateDTO';
import { CertificateDTO } from '../model/CertificateDTO';
import { revokeCertificateDTO } from '../dto-interfaces/revokeCertificateDTO';


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
  // rootCertDTO:rootCertificateDTO= {
  //   adminMail:'',
  //   startDate:new Date(),
  //   endDate:new Date(),
  //   keyUsages:[],
  //   extendedKeyUsages:[]
  // }
  keyUsagebool:boolean=false;
  extKeyUsagebool:boolean=false;
  allCertificates: CertificateDTO[]= [];
  selectedCertificate!:CertificateDTO;
  filteredCertificates:CertificateDTO[]=[];
  allCertificatesReserve:CertificateDTO[]=[];
  filterCounter=0;

  revokeCertDTO:revokeCertificateDTO={
    certificateType:'',
    subjectMail:'',
    certificateSerialNumber:'',
    reason:''
  }
  revocationReasonFlag:boolean=false;

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

  openModal(cert:CertificateDTO){    
    this.selectedCertificate=cert;  
    this.toggleDetailWin=true;
  }
  closeModal(){       
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
  
  keyUsageCBoxes(){
    this.extKeyUsagebool=false
    this.keyUsagebool=true
  }

  keyUsageExtCBoxes(){
    this.keyUsagebool=false;
    this.extKeyUsagebool=true
  }

  selectUser(){

  }

  returnToPreviousPhase(){
    this.phase-=1;
  }

  downloadCertificate(){
    this.adminService.downloadCertificateWeb(this.selectedCertificate.serialNumber).
      subscribe(blob => saveAs(blob, "Certificate_"+this.selectedCertificate.serialNumber+".cer"));
  }

  // addKeyExt(extension:string){
  //   if(this.rootCertDTO.keyUsages.find(x => x===extension)){      
  //     this.rootCertDTO.keyUsages.forEach((el, index) => {if (el===extension){
  //       this.rootCertDTO.keyUsages.splice(index,1)        
  //     }})
  //     return;
  //   }   
  //   this.rootCertDTO.keyUsages.push(extension)
    
  // }

  // addKeyExtExtended(extension:string){
  //   if(this.rootCertDTO.extendedKeyUsages.find(x => x===extension)){      
  //     this.rootCertDTO.extendedKeyUsages.forEach((el, index) => {if (el===extension){
  //       this.rootCertDTO.extendedKeyUsages.splice(index,1)        
  //     }})
  //     return;
  //   }  
  //   this.rootCertDTO.extendedKeyUsages.push(extension)
  // }

  // makeRootCertificate(){   
  //   this.rootCertDTO.adminMail = localStorage.getItem('mail');
  //   this.adminService.addRootCertificate(this.rootCertDTO);
  // }

  loadCertificates(){
    this.adminService.getAllCertificates().subscribe( (data) =>{this.allCertificates=data; this.allCertificatesReserve=data}, (error) => {console.log(error);
    })
  }

  returnCertificateForView(): CertificateDTO{
    return this.selectedCertificate;
  }

  refreshList(){
    this.allCertificates=this.allCertificatesReserve;
  }

  filter(criteria:string){
    
    this.allCertificates=this.allCertificatesReserve;
     
    this.filteredCertificates=[]
    if(criteria==='root'){
      this.allCertificates.forEach((element, index) => {
          if(element.certificateType==='ROOT_CERTIFICATE'){
            this.filteredCertificates.push(this.allCertificates[index])            
          }
      });
      this.allCertificates=this.filteredCertificates;
    } else if(criteria==='ca'){
      this.allCertificates.forEach((element, index) => {
        if(element.certificateType==='CA_CERTIFICATE'){
          this.filteredCertificates.push(this.allCertificates[index])  
        }
    });
    this.allCertificates=this.filteredCertificates;
    } else {
      this.allCertificates.forEach((element, index) => {
        if( element.certificateType==='END_ENTITY_CERTIFICATE'){
          this.filteredCertificates.push(this.allCertificates[index])  
        }
    });
    this.allCertificates=this.filteredCertificates;
    }
  }

  addRevocReason(reason:string){
    this.revokeCertDTO.reason=reason;
  }

  collectRevocationData(certificateDTO:CertificateDTO){
    this.revocationReasonFlag=true;
    this.revokeCertDTO.certificateSerialNumber=certificateDTO.serialNumber;
    this.revokeCertDTO.certificateType=certificateDTO.certificateType;
    this.revokeCertDTO.subjectMail=certificateDTO.subjectData.email;   
  }

  revokeCertificate(){
    this.adminService.revokeCertificate(this.revokeCertDTO);
  }

  ngOnInit(): void {
    this.loadCertificates();
  }

}
