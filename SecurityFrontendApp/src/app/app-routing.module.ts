import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login/login-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { ClientHomePageComponent } from './pages/client/client-home-page.component';
import { AddRootCertificateComponent } from './admin-home-page/add-root-certificate/add-root-certificate.component';
import { AddCertificateComponent } from './admin-home-page/add-certificate/add-certificate.component';


const routes: Routes = [
  {path: '', component: LoginPageComponent},
  {path: 'admin',component: AdminHomePageComponent},
  {path: 'client',component: ClientHomePageComponent},
  {path: 'add-root-certificate',component: AddRootCertificateComponent},
  {path: 'add-certificate',component: AddCertificateComponent},
  {path: 'all-certificates',component: AdminHomePageComponent},
  {path: 'my-certificates',component:ClientHomePageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
