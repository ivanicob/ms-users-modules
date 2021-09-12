import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private userService: UserService) { 
  }

  ngOnInit(): void {
  }

  onLogout() {
    this.userService.logOut();
    this.router.navigateByUrl('/login');
  }

}
