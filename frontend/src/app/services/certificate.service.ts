import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IssuerBasicInfo } from '../model/issuer-basic-info.model';
import { ConstantsService } from './constants-service.service';
import { IssuerSubjectData } from '../model/issuer-subject-data.model';
import { Certificate } from '../model/certificate.model';
import { TreeNode } from '../model/TreeNode.model';
import { RecommendedVadility } from '../model/recommended-vadility.model';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient, private constants: ConstantsService) { }

  getValidIssuers(): Observable<IssuerBasicInfo[]> {
    return this.http.get<IssuerBasicInfo[]>(this.constants.certificatesPath + '/issuers/valid');
  }

  getFullIssuerData(certSerialNumber: string): Observable<Certificate> {
    return this.http.get<Certificate>(this.constants.certificatesPath + '/' + certSerialNumber);
  }

  getTree(): Observable<TreeNode[]> {
    return this.http.get<TreeNode[]>(this.constants.certificatesPath + '/tree');
  }

  getValidity(): Observable<RecommendedVadility> {
    return this.http.get<RecommendedVadility>(this.constants.certificatesPath + '/validity');
  }

  addCertificate(certificate: Certificate): Observable<any> {
    return this.http.post<any>(this.constants.certificatesPath, certificate);
  }

  // tslint:disable-next-line: ban-types
  revoke(serialNumber: String) {
    return this.http.put(this.constants.certificatesPath + '/revoke/' + serialNumber, {});
  }
}
