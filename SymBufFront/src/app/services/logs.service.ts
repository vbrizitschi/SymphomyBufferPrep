import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private http: HttpClient) { }

  getCalculationLogs():Observable<any> {
    return this.http.get(`${environment.baseUrl}/logs/calculations`);
  }
}
