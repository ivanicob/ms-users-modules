import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SubSink } from 'subsink';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  private subscriptions = new SubSink();
  public users: any;

  constructor(private router: Router, private userService: UserService) { 
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  public loadUsers(): void{

    this.subscriptions.add(
      this.userService.findAll()
      .subscribe(
        (result: any) => {
          this.users = Object.values(JSON.parse(result)['data']);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
        //this.errorMessage = error.message;
      })
    );
  }

  onLogout() {
    this.userService.logOut();
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }   

}
