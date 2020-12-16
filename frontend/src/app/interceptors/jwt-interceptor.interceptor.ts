import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(
        private authenticationService: AuthenticationService
      ) {}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const item = this.authenticationService.getToken();
        const decodedItem = JSON.parse(item);
        console.log('Interpreter: ' + JSON.stringify(decodedItem));

        if (item) {
            const cloned = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.authenticationService.getToken()}`
                  }
            });

            return next.handle(cloned);
        } else {
            return next.handle(req);
        }
    }

}
