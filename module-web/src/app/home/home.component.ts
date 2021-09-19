import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { SubSink } from 'subsink';
import { User } from '../models/User';
import { UserService } from '../services/user.service';
import { Convert } from '../util/HttpErrorResponseConvert';

interface RoleInterface {
  id: string,
  value: string
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  private subscriptions = new SubSink();
  public users: any;
  public showUser: User;
  public editUser: User;
  public deleteUser: User;  
  public roles: RoleInterface[];
  public selectedRole: RoleInterface;
  public current_date: Date;

  errorMessage = '';
  invalidMessage = false;

  constructor(private router: Router, private userService: UserService) { 

    this.roles = [
      {
        id: "ROLE_USER",
        value: "Member"
      },
      {
        id: "ROLE_ADMIN",
        value: "Admin"
      }
    ];

    this.current_date = new Date();
  
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
      }, 
      (error: HttpErrorResponse) => {
        console.info('---------> HttpErrorResponse : ' + error);
        this.setErrorMessage(error);
      })
    );
  }

  public resetErrorMessage(): void {
    this.errorMessage = '';
    this.invalidMessage = false;    
  }

  public setErrorMessage(message: HttpErrorResponse): any {
    this.invalidMessage = true;
    this.errorMessage = Convert.toError(message); 
  }

  public onAddUser(addForm: User): void {
    this.resetErrorMessage();
    document.getElementById('add-user-form').click();

    this.userService.addUser(addForm).subscribe(  
      (response: User) => {
        console.log(response);
        this.loadUsers();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.info('---------> HttpErrorResponse : ' + error);
        this.setErrorMessage(error);
        addForm.reset();
      }
    );
  }

  public onUpdateUser(updateForm: User): void {
    this.resetErrorMessage();
    this.userService.updateUser(updateForm).subscribe(
      (response: User) => {
        console.log(response);
        this.loadUsers();
      },
      (error: HttpErrorResponse) => {
        console.info('---------> HttpErrorResponse : ' + error);
        this.setErrorMessage(error);
      }
    );
  }

  public onDeleteUser(userId: number): void {
    this.resetErrorMessage();
    this.userService.deleteUser(userId).subscribe(
      (response: void) => {
        console.log(response);
        this.loadUsers();
      },
      (error: HttpErrorResponse) => {
        console.info('---------> HttpErrorResponse : ' + error);
        this.setErrorMessage(error);
      }
    );
  }

  public searchUsers(key: string): void {
    this.resetErrorMessage();
    console.log(key);
    const results: User[] = [];
    for (const user of this.users) {
      if (user.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || user.email.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(user);
      }
    }
    this.users = results;
    if (results.length === 0 || !key) {
      this.loadUsers();
    }
  }

  public onOpenModal(user: User, mode: string): void {
    this.resetErrorMessage();

    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addUserModal');
    }
    if (mode === 'show') {
      this.showUser = user;
      button.setAttribute('data-target', '#showUserModal');
    }    
    if (mode === 'edit') {
      this.editUser = user;
      button.setAttribute('data-target', '#updateUserModal');
    }
    if (mode === 'delete') {
      this.deleteUser = user;
      button.setAttribute('data-target', '#deleteUserModal');
    }
    container.appendChild(button);
    button.click();
  }  

  onLogout() {
    this.userService.logOut();
    this.router.navigateByUrl('/login');
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }   

}
