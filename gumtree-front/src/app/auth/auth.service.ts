import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

export type User = { email: string, password: string, nick: string };

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  currentUser: User;

  constructor(private httpClient: HttpClient, private router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('userData'));
  }

  public login(mail: string, password: string): Observable<any> {
    localStorage.removeItem('userData');
    this.currentUser = null;

    return this.httpClient.get<any>(
      "http://localhost:8080/api/current-user",
      {
        headers: {'Authorization': 'Basic ' + btoa(mail + ":" + password)}
      }
    ).pipe(tap<any>(response => {
      this.currentUser = {email: mail, password: password, nick: response.nick}
      localStorage.setItem('userData', JSON.stringify(this.currentUser));
    }))
  }

  createNewUser(newUserData: any) {
    return this.httpClient.post<any>(
      "http://localhost:8080/api/users",
      newUserData
    );
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem('userData');
  }

  isLoggedUser(): boolean {
    return !!this.currentUser;
  }

  getBasicAuthToken() {
    return btoa(this.currentUser.email + ":" + this.currentUser.password);
  }


}
