import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './login-page/login-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { ClientHomePageComponent } from './client-home-page/client-home-page.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'adminHome', component: AdminHomePageComponent },
  { path: 'clientHome', component: ClientHomePageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
