import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    public username!: string;
    public password!: string;

  constructor(private http: HttpClient) {

  }

  login(username: string, password: string) {
    return this.http.post(environment.hostUrl + `/api/v1/authenticate`, new User(username, password));




      // { headers: { authorization: this.createBasicAuthToken(username, password) } }).pipe(map((res) => {
      //   this.username = username;
      //   this.password = password;
      //   this.registerSuccessfulLogin(username, password);
      // }));
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ":" + password);
  }

  registerSuccessfulLogin(username: string, password: string) {
    // save the username to redis session
  }

  // user(username: string, password: string) {
  //   username = username,
  //   password = password  
  // }


}
