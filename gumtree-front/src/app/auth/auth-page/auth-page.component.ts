import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-auth-page',
  templateUrl: './auth-page.component.html'
})
export class AuthPageComponent implements OnInit {
  authFormGroup: FormGroup;
  isLoading:boolean = false;
  errorMessage: string;
  successMessage: string;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authFormGroup = new FormGroup({
      'mail': new FormControl(),
      'password': new FormControl()
    })
  }

  login() {
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = true;
    this.authService.login(this.authFormGroup.get('mail').value, this.authFormGroup.get('password').value)
      .subscribe(
        successResponse => {
          console.log(successResponse);
          this.isLoading = false;
          this.successMessage = "Logowanie zakończone powodzeniem";
        },
        errorResponse => {
          console.log(errorResponse);
          if(errorResponse.error?.errorMessage){
            console.log(errorResponse.error);
            this.errorMessage = errorResponse.error.errorMessage;
          } else {
            this.errorMessage = "Nieprawidłowa nazwa użytkownika lub hasło!";
          }
          this.isLoading = false;
        }
      )
  }

  registerUser() {
    this.errorMessage = '';
    this.successMessage = '';
    this.isLoading = true;
    let rawValue = this.authFormGroup.getRawValue();
    console.log(rawValue);
    this.authService.createNewUser(rawValue).subscribe(
      response => {
        console.log(response);
        this.isLoading = false;
        this.successMessage = "Wysłaliśmy na podany adres e-mail link aktywacyjny. Kliknij go, aby dokończyć proces rejestracji";
      },
      errorResponse => {
        console.log(errorResponse);
        if(errorResponse.error?.errorMessage){
          console.log(errorResponse.error);
          this.errorMessage = errorResponse.error.errorMessage;
        } else {
          this.errorMessage = "Wystąpił problem podczas rejestracji użytkownika!";
        }
        this.isLoading = false;
      },
    );
  }

  logout():void{
    this.errorMessage = '';
    this.successMessage = '';
    this.authService.logout();
  }

  isLoggedUser() :boolean{
    return this.authService.isLoggedUser();
  }

  getUsername(): string{
    return this.authService.currentUser.nick;
  }
}
