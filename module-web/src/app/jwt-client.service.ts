import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private http:HttpClient) {}

    public generateToken(request:any){
      return this.http.post("http://localhost:8080/user-service/api/v1/authenticate", request, {responseType: 'text' as 'json'});
    }

    public welcome(token:any) {

      let tokenStr = 'Bearer ' + token;
      const headers = new HttpHeaders().set("Authorization", tokenStr);

      return this.http.post("http://localhost:8080/",  headers, {responseType: 'text' as 'json'});
    }

}
