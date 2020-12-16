import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IssuerBasicInfo } from '../model/issuer-basic-info.model';
import { ConstantsService } from './constants-service.service';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private http: HttpClient, private constants: ConstantsService) { }

  getLogs(params: string): Observable<any> {
    return this.http.get<any>(this.constants.logsPath + '?' + params);
  }

  getReports(params: string): Observable<any> {
    return this.http.get<any>(this.constants.logsPath + '/report' + '?' + params);
  }

}
