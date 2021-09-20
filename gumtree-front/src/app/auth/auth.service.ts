import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {User} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user = new BehaviorSubject<User>(null);

  constructor(private httpClient: HttpClient) {
  }

  createNewUser(userData: { login: string, password: string }): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/users', userData)
      .pipe(catchError(errorRes => {
        let errorMessage = "Wystąpił nieoczekiwany błąd";
        if (errorRes.message) {
          errorMessage = errorRes.message;
        }
        return throwError(errorMessage);
      }), tap(res => this.handleAuthentication(userData.login, userData.password)))
  }

  login(login: any, password: any) {
    return this.httpClient.get("http://localhost:8080/api/current-user", {headers: {'Authorization': 'Basic ' + btoa(login + ":" + password)}})
      .subscribe(res => this.handleAuthentication(login, password));

      // .pipe(tap(res => {
      //   console.log(res);
      //   this.handleAuthentication(login, password);
      // }))
  }

  getUsers(){
    console.log('get users');
    this.httpClient.get('http://localhost:8080/api/users').subscribe(res => console.log(res));
  }

  private handleAuthentication(login: string, password: string){
    let user = new User(login, login, btoa(login + ":" + password));
    console.log(this.user);
    localStorage.setItem('userData', JSON.stringify(user));
  }
}
