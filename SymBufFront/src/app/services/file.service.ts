import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {SseService} from "./sse.service";


@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient,
              private sseService: SseService) { }

  uploadBuffers(file: File): Observable<any>{
    const formData: FormData = new FormData();
    formData.append('file',file);
    return this.http.post(`${this.baseUrl}/documents/loadBuffer`, formData);
  }

  uploadMinBufferSSE(file:File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file',file);
    const url = `${this.baseUrl}/documents/loadMinBuffer`;
    return this.sseService.getServerSentEvent(url, formData);
  }
  downloadFile(templateName: string) {
    return this.http.get(`${environment.baseUrl}/documents/download-template?doc_name=${templateName}`, {responseType: 'blob'});
  }

}
