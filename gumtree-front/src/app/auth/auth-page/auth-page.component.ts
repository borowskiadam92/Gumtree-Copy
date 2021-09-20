import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth-page',
  templateUrl: './auth-page.component.html'
})
export class AuthPageComponent implements OnInit {
  authFormGroup: FormGroup;
  isLoading:boolean = false;
  errorMessage: string;

  constructor(private authSesrvice: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.authFormGroup = new FormGroup({
      'mail': new FormControl(),
      'password': new FormControl()
    })
  }

  login() {
    console.log("login in progress");
    this.authSesrvice.login(this.authFormGroup.get('mail').value, this.authFormGroup.get('password').value);
    this.router.navigate(['/']);
  }

  registerUser() {
    this.errorMessage = '';
    this.isLoading = true;
    let rawValue = this.authFormGroup.getRawValue();
    console.log(rawValue);
    this.authSesrvice.createNewUser(rawValue).subscribe(
      response => {
        console.log(response);
        this.isLoading = false;
      },
      errorMessage => {
        console.log(errorMessage);
        this.errorMessage = errorMessage;
        this.isLoading = false;
      },
    );
  }
}
