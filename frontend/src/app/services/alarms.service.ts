import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants-service.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlarmsService {

  constructor(private http: HttpClient, private constants: ConstantsService) { }

  getAlarms(): Observable<any> {
    return this.http.get<any>(this.constants.alarmsPath);
  }
}
