import {Injectable, NgZone} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {LoginService} from "./login.service";
import {StorageService} from "./storage.service";

declare let SSE: any;

@Injectable({
  providedIn: 'root'
})
export class FileService {

  eventSource = SSE;

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient,
              private zone: NgZone,
              private storageService: StorageService) { }

  uploadBuffers(file: File): Observable<any>{
    const formData: FormData = new FormData();
    formData.append('file',file);
    return this.http.post(`${this.baseUrl}/documents/loadBuffer`, formData);
  }

  uploadMinBuffers(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.baseUrl}/documents/loadMinBuffer`, formData);
  }



  downloadFile(templateName: string) {
    return this.http.get(`${environment.baseUrl}/documents/download-template?doc_name=${templateName}`, {responseType: 'blob'});
  }

  testEss(): Observable<any> {
    return this.http.get('http://localhost:8080/sse/words');
  }

  uploadMinBufferSSE(file:File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file',file);
    const url = `${this.baseUrl}/documents/loadMinBuffer`;
    return this.getServerSentEvent(url, formData);
  }

  private getServerSentEvent(url: string, data: FormData): Observable<any> {
    return Observable.create((observer:any) => {
      const eventSource = this.getEventSourceWithPost(url, data);
      // Launch query
      eventSource.stream();
      // on answer from message listener
      eventSource.onmessage = (event:any) => {
        this.zone.run(() => {
          observer.next(event.data);
        });
      };
      eventSource.onerror = (error:any) => {
        this.zone.run(() => {
          observer.error(error);
        });
      };
    });
  }


  protected closeConnection(eventSource:any): void {
    this.closeEventSource();
  }

  getEventSourceWithPost(url:string, formData:FormData) {
    return this.buildEventSource(url,'POST', formData);
  }

  private buildEventSource(url: string, meth: string, formData: FormData) {
    const options = this.buildOptions(meth, formData);
    this.eventSource = new SSE(url, options);

    // add listener
    this.eventSource.addEventListener('message', (e:any) => {
      return e.data;
    });

    return  this.eventSource;
  }

  public closeEventSource() {
    if (!! this.eventSource) {
      this.eventSource.close();
    }
  }

  private buildOptions(
    meth: string,
    formData: FormData,
  ): {
    payload: FormData;
    method: string;
    headers: string | { Authorization: string};
  } {

    const token = this.storageService.getItem('token');

    return {
      payload: formData,
      method: meth,
      headers: {Authorization: 'Bearer '+token}
    };
  }



}
