import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../service/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor( private router:Router, private loginService:LoginService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {         
      if(this.loginService.isUserLoggedIn()){   
        if(route.routeConfig?.path==='admin' && route.data.role[0]===this.loginService.getCurrentUser().role){
          return true
        } else
        if(route.routeConfig?.path==='add-root-certificate' && route.data.role[0]===this.loginService.getCurrentUser().role){
            return true
          } else
        if(route.routeConfig?.path==='all-certificates' && route.data.role[0]===this.loginService.getCurrentUser().role){
            return true
          } else
        if(route.routeConfig?.path==='add-certificate' && (route.data.role[0]===this.loginService.getCurrentUser().role || route.data.role[1]===this.loginService.getCurrentUser().role)){
            return true
          } else       
        if(route.routeConfig?.path==='my-certificates' && route.data.role[0]===this.loginService.getCurrentUser().role){
            return true
          } else
        if(route.routeConfig?.path==='client' && route.data.role[0]===this.loginService.getCurrentUser().role){                 
          return true
        } else{       
          this.router.navigate(['/not-authorized'])
          return false;
        }       
      }
      else {
        this.router.navigate(['/'])
        return false
      }
  }
  
}