import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SubSink } from 'subsink';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private subscriptions = new SubSink();
  showLoading: boolean;

  username: string = '';
  password: string = '';
  errorMessage = 'Username or password is incorrect!';
  successMessage!: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    if(this.userService.isLogedIn()){
      this.router.navigateByUrl('/home')
    }else{
      this.router.navigateByUrl('/login')
    }
  }

  onLogin(user: User) {

    this.subscriptions.add(
      this.userService.login(user).subscribe(
        (result) => {

          this.userService.addTokenToCache(result.body.token);
          this.userService.addUserToCache(result.body);
          this.router.navigateByUrl('/home');
          this.showLoading = false;

          this.invalidLogin = false;
          this.loginSuccess = true;
          this.successMessage = 'Login Successful';

      }, (error: HttpErrorResponse) => {
        this.showLoading = false;

        this.invalidLogin = true;
        this.loginSuccess = false;
        this.errorMessage = error.message;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }  

}
