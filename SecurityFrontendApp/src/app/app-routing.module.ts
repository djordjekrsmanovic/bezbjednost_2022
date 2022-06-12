import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login/login-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { ClientHomePageComponent } from './pages/client/client-home-page.component';
import { AddRootCertificateComponent } from './admin-home-page/add-root-certificate/add-root-certificate.component';
import { AddCertificateComponent } from './admin-home-page/add-certificate/add-certificate.component';
import { AuthGuard } from './authorization/auth.guard';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';

const routes: Routes = [
  {path: '', component: LoginPageComponent},
  {path: 'admin',component: AdminHomePageComponent, canActivate:[AuthGuard], data:{role:['ADMIN']}},
  {path: 'client',component: ClientHomePageComponent, canActivate:[AuthGuard], data:{role:['USER']}},
  {path: 'add-root-certificate',component: AddRootCertificateComponent, canActivate:[AuthGuard], data:{role:['ADMIN']}},
  {path: 'add-certificate',component: AddCertificateComponent, canActivate:[AuthGuard], data:{role:['ADMIN', 'USER']}},
  {path: 'all-certificates',component: AdminHomePageComponent, canActivate:[AuthGuard], data:{role:['ADMIN']}},
  {path: 'my-certificates',component:ClientHomePageComponent, canActivate:[AuthGuard], data:{role:['USER']}},
  {path:  'not-authorized', component: NotAuthorizedComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
