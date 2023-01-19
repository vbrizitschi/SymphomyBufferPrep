import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, retry, share, Subject, switchMap, tap, timer} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl: string = `${environment.baseUrl}/documents/re-build-file`;

  constructor(private http: HttpClient) { }

  uploadFile(file: File): Observable<any>{
    const formData: FormData = new FormData();
    formData.append('file',file);
    return this.http.post(this.baseUrl, formData);
  }

  downloadFile(templateName: string) {
    return this.http.get(`${environment.baseUrl}/documents/download-template?doc_name=${templateName}`, {responseType: 'blob'});
  }

  testEss(): Observable<any> {
    return this.http.get('http://localhost:8080/sse/words');
  }
}
