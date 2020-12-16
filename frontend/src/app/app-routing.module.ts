import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCertificateFormComponent } from './add-certificate-form/add-certificate-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomeComponent } from './home/home.component';
import { RoleGuard } from './guards/role.guard';
import { CertificatesTreeViewComponent } from './certificates-tree-view/certificates-tree-view.component';
import { LogsComponent } from './logs/logs.component';
import { LogReportsComponent } from './log-reports/log-reports.component';
import { LogReportsPageComponent } from './log-reports-page/log-reports-page.component';

import { AlarmsComponent } from './alarms/alarms.component';



const routes: Routes = [
  {path: '', component: HomeComponent },
  {path: 'login-form', component: LoginFormComponent},
  {path: 'home', component: HomeComponent},
  {
    path: 'add-certificate-form',
    component: AddCertificateFormComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMINISTRATOR'}
  },
  {
    path: 'certificates-tree',
    component: CertificatesTreeViewComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMINISTRATOR'}
  },
  {
    path: 'logs',
    component: LogsComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMINISTRATOR|ROLE_OPERATOR'}
  },
  {

    path: 'log-reports',
    component: LogReportsPageComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMINISTRATOR|ROLE_OPERATOR'}
  },{

    path: 'alarms',
    component: AlarmsComponent,
    canActivate: [RoleGuard],
    data: {expectedRoles: 'ROLE_ADMINISTRATOR'}
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
