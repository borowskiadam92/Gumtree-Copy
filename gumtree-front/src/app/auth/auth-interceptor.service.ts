import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(`is logged ? ${this.authService.isLoggedUser()}`)
    if (this.authService.isLoggedUser()) {
      req = req.clone({
        setHeaders: {
          'Authorization': 'Basic ' + this.authService.getBasicAuthToken()
        }
      });
    }
    console.log(req);
    return next.handle(req);
  }

}
