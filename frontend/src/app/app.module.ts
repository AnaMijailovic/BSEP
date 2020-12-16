import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { ToastrModule } from 'ngx-toastr';
import { ReactiveFormsModule } from '@angular/forms';
import { AddCertificateFormComponent } from './add-certificate-form/add-certificate-form.component';
import { CertificateService } from './services/certificate.service';
import { ConstantsService } from './services/constants-service.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginFormComponent } from './login-form/login-form.component';
import { NavbarAdministratorComponent } from './navbar-administrator/navbar-administrator.component';
import { NavbarOperatorComponent } from './navbar-operator/navbar-operator.component';
import { HomeComponent } from './home/home.component';
import { NavbarUnregisteredComponent } from './navbar-unregistered/navbar-unregistered.component';
import { AuthenticationService } from './services/authentication.service';
import { JwtInterceptor } from './interceptors/jwt-interceptor.interceptor';
import { CertificatesTreeViewComponent } from './certificates-tree-view/certificates-tree-view.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { LogsComponent } from './logs/logs.component';
import { LogsService } from './services/logs.service';
import { SearchLogsFormComponent } from './search-logs-form/search-logs-form.component';
import { LogsTableComponent } from './logs-table/logs-table.component';
import { LogReportsComponent } from './log-reports/log-reports.component';
import { LogReportsPageComponent } from './log-reports-page/log-reports-page.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { ChartsModule } from 'ng2-charts';
import { AlarmsComponent } from './alarms/alarms.component';
import { AlarmsTableComponent } from './alarms-table/alarms-table.component';


@NgModule({
  declarations: [
    AppComponent,
    AddCertificateFormComponent,
    LoginFormComponent,
    NavbarAdministratorComponent,
    NavbarOperatorComponent,
    HomeComponent,
    NavbarUnregisteredComponent,
    CertificatesTreeViewComponent,
    ConfirmationDialogComponent,
    LogsComponent,
    SearchLogsFormComponent,
    LogsTableComponent,
    LogReportsComponent,
    LogReportsPageComponent,
    BarChartComponent,
    AlarmsComponent,
    AlarmsTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    ChartsModule,
    ToastrModule.forRoot({
      progressBar: true,
      timeOut: 4000,
      closeButton: true,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true
    }),
  ],
  providers: [
    ConstantsService,
    CertificateService,
    LogsService,
    AuthenticationService, {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],

  entryComponents: [ConfirmationDialogComponent ],
  bootstrap: [AppComponent]
})
export class AppModule { }
