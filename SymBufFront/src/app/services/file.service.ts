import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl: string = 'http://symphonyserver:8080/api/documents/re-build-file'

  constructor(private http: HttpClient) { }

  uploadFile(file: File): Observable<any>{
    const formData: FormData = new FormData();
    formData.append('file',file);
    return this.http.post(this.baseUrl, formData);
  }
}
