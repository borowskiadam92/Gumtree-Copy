import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
} from '@angular/common/http';

import { AuthService } from './auth.service';
import {User} from "./user.model";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    // if(localStorage.getItem('userData')){
    //   let user: User = JSON.parse(localStorage.getItem('userData'));
    //   console.log(user);
    //   req = req.clone({
    //     setHeaders: {
    //       'Authorization': 'Basic ' + user.token,
    //     }});
    // }
    console.log(req);
    return next.handle(req);
  }
}
