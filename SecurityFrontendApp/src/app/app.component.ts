import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private loginService: LoginService, private router: Router){}
  logged:boolean=false;

  ngOnInit(): void{
    if(localStorage.getItem('logedIn')==='true'){
      this.logged=true;
    }
  }

  logOut(){
    this.loginService.logout()
  }
  
  title = 'SecurityFrontendApp';
}
