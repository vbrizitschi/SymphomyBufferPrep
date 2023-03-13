import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {SseService} from "./sse.service";
import {CalculateBuffer} from "../interfaces/calculate-buffer";
import {Ctxt2} from "../interfaces/ctxt2";
import {StockLocation} from "../interfaces/stockLocation";



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

  calculateBuffer(calculateBuffer: CalculateBuffer): Observable<any>{
    const formData: FormData = new FormData();
    formData.append('runCalculateBufferDTO', JSON.stringify(calculateBuffer));

    return this.sseService.getServerSentEventJSON(`${this.baseUrl}/documents/runCalculateBuffer`, formData);

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

  getAllSL():Observable<StockLocation[]>{
    return this.http.get<StockLocation[]>(`${this.baseUrl}/documents/get-sl`)
  }

  getAllCtxt():Observable<Ctxt2[]>{
    return this.http.get<Ctxt2[]>(`${this.baseUrl}/documents/get-ctxt2`)
  }

}
