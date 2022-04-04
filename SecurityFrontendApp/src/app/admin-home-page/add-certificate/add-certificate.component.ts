import { Component, OnInit } from '@angular/core';
import { rootCertificateDTO } from 'src/app/dto-interfaces/rootCertificateDTO';
import { CertificateDTO } from 'src/app/model/CertificateDTO';
import { UserDto } from 'src/app/model/UserDto';
import { ClientService } from 'src/app/service/client.service';
import { AdminService } from '../admin-service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {


  constructor(private adminService:AdminService,private clientService:ClientService) { }

  userTableIndex:string='';

  dateFrom:Date=new Date();

  dateTo:Date=new Date();

  todayDate=new Date();

  certificateTableIndex:string='';

  toggleDetailWin:boolean=false;

  selectedCertificateForDetails:any;

  selectedIssuerCertficate:any;

  allCertificates: CertificateDTO[]= [];

  users:UserDto[]=[];

  selectedUser:UserDto={
    id:'-1',
    mail:'',
    name:'',
    country:'',
    organizationUnit:''
  };

  
  datePickerChanged(){
    console.log(this.dateFrom);
    console.log(this.dateTo);
  }

  rootCertDTO:rootCertificateDTO= {
    adminMail:'',
    startDate:new Date(),
    endDate:new Date(),
    keyUsages:[],
    extendedKeyUsages:[]
  }

  ngOnInit(): void {
    this.loadCertificates();
    this.loadUsers();
  }

  loadUsers(){
    this.clientService.getUsers().subscribe(data => {this.users=data}, error => {console.log(error)});
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

  rowUserClicked(k:string,user:UserDto){
    this.selectedUser=user;
    console.log(this.selectedUser);
    document.getElementById(this.userTableIndex)?.classList.toggle("rowColor");
    this.userTableIndex=k;
    document.getElementById(this.userTableIndex)?.classList.toggle('rowColor');
    
  }

  rowCertificateClicked(k:string,certificate:any){

    this.selectedIssuerCertficate=certificate;
    console.log(this.selectedIssuerCertficate);
    document.getElementById(this.certificateTableIndex)?.classList.toggle("rowColor");
    this.certificateTableIndex=k;
    document.getElementById(this.certificateTableIndex)?.classList.toggle('rowColor');
    
  }

  openModal(cert:CertificateDTO){    
    this.selectedCertificateForDetails=cert;  
    this.toggleDetailWin=true;
  }
  closeModal(){       
     this.toggleDetailWin=false;
  }

  returnCertificateForView(): CertificateDTO{
    return this.selectedCertificateForDetails;
  }

  loadCertificates(){
    this.adminService.getAllCertificates().subscribe( (data) =>{this.allCertificates=data}, (error) => {console.log(error);
    })
  }
}
