import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { ConstantsService } from './constants-service.service';
import { Observable } from 'rxjs';
import { Login } from '../model/login.model';
import { PasswordChanger } from '../model/password-changer.model';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private http: HttpClient,
    private constantsService: ConstantsService
  ) { }

  login(auth: any): Observable<any> {
    console.log(auth.username);
    console.log(auth.password);
    return this.http.post(this.constantsService.authPath + '/login',
    {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'text'});
  }

  logout(): Observable<any> {
    return this.http.get(this.constantsService.authPath + '/logout', {headers: this.headers, responseType: 'text'});
  }

  changePassword(changePw: PasswordChanger): Observable<any> {
    return this.http.post(this.constantsService.authPath + '/change-password', changePw,
      {headers: this.headers, responseType: 'json'});
  }


  isLoggedIn(): boolean {
    if (localStorage.getItem('user')) {
        return true;
    }
    return false;
  }

  getToken(): string {
    return localStorage.getItem('user');
  }

  getRole(): string {
    const token = this.getToken();
    console.log(token);
    const jwt: JwtHelperService = new JwtHelperService();

    if (!token) {
      return 'NO_ROLE';
    }
    console.log(jwt.decodeToken(token).role);
    return jwt.decodeToken(token).role;
  }
}
