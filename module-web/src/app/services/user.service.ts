import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private hostUrl = environment.hostUrl;

  constructor(private http: HttpClient) { }

  login(user: User): Observable<HttpResponse<User>>{
    return this.http.post<User>(`${this.hostUrl}/api/v1/authenticate`, user, {observe: 'response'});
  }

  logOut(): void{
    this.removeUserToCache();
    this.removeTokenToCache();
  }

  findById(id: number): Observable<any>{
    return this.http.get<any>(`${this.hostUrl}/api/v1/user/${id}`, this.jwt());
  }
  
  findAll(): Observable<any>{
    return this.http.get<any>(`${this.hostUrl}/api/v1/users`, this.jwt());
  }  

  addUser(formData: FormData): Observable<User>{
    return this.http.post<User>(`${this.hostUrl}/api/v1/users`, formData, this.jwt());
  }

  updateUser(formData: FormData): Observable<User>{
    return this.http.put<User>(`${this.hostUrl}/api/v1/user`, formData, this.jwt());
  }

  deleteUser(id: number): Observable<any>{
    return this.http.delete<any>(`${this.hostUrl}/api/v1/user/${id}`, this.jwt());
  } 

  addUserToCache(user: User): void{
    localStorage.setItem('CURRENT_USER', JSON.stringify(user))
  }

  removeUserToCache(): void{
    localStorage.removeItem('CURRENT_USER');
  } 

  getUserFromCache(): User {
    return JSON.parse(localStorage.getItem('CURRENT_USER') || '{}');
  }

  addTokenToCache(token: String): void{
    localStorage.setItem('ACCESS_TOKEN', JSON.stringify(token));
  }

  removeTokenToCache(): void{
    localStorage.removeItem('ACCESS_TOKEN');
  }

  getTokenFromCache(): String {
    return JSON.parse(localStorage.getItem('ACCESS_TOKEN') || '{}');
  } 

  isLogedIn(): boolean {
    let currentUser = JSON.parse(localStorage.getItem('CURRENT_USER') || '{}');
    return (currentUser && currentUser.token);
  }

  // private helper methods

  private jwt() {
    // create authorization header with jwt token
    if (this.isLogedIn()) {
      return {
        headers: new HttpHeaders({ 'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('ACCESS_TOKEN') || '{}') }),
        responseType: 'text' as 'json'
      } || {}
    }

    return {};
  }



}
