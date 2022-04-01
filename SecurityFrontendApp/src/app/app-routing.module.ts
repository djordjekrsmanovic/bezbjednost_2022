import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login/login-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { ClientHomePageComponent } from './pages/client/client-home-page.component';


const routes: Routes = [
  {path: '', component: LoginPageComponent},
  {path: 'admin',component: AdminHomePageComponent},
  {path: 'client',component: ClientHomePageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
