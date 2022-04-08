import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginPageComponent } from './pages/login/login-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { ClientHomePageComponent } from './pages/client/client-home-page.component';
import { FormsModule } from '@angular/forms';
import { CertificateComponent } from './components/certificate/certificate.component';
import { AddRootCertificateComponent } from './admin-home-page/add-root-certificate/add-root-certificate.component';
import { AddCertificateComponent } from './admin-home-page/add-certificate/add-certificate.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    AdminHomePageComponent,
    ClientHomePageComponent,
    CertificateComponent,
    AddRootCertificateComponent,
    AddCertificateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
