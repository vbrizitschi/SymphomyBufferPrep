import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../interfaces/user";


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  login(user: User): Observable<any>  {
    return this.http.post(`${this.baseUrl}/auth/performLogin` , user);
  }

  isAuthenticated(): boolean {
    return true;
  }



}
