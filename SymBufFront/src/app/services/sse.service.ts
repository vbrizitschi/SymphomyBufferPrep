import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs";
import {StorageService} from "./storage.service";

declare let SSE: any;
@Injectable({
  providedIn: 'root'
})
export class SseService {

  eventSource = SSE;

  constructor( private zone: NgZone,
               private storageService: StorageService) { }

  public getServerSentEvent(url: string, data: FormData): Observable<any> {
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
          this.closeEventSource();
        });
      };
      eventSource.oncomplete = () => {
        this.zone.run(() => {
          observer.complete();
          this.closeEventSource();
        })
      }
    });
  }

  private getEventSourceWithPost(url:string, formData:FormData) {
    return this.buildEventSource(url,'POST', formData);
  }

  private buildEventSource(url: string, meth: string, formData: FormData) {
    const options = this.buildOptions(meth, formData);
    this.eventSource = new SSE(url, options);

    // add listener
    this.eventSource.addEventListener('message', (e:any) => {
      return e.data;
    });

    this.eventSource.addEventListener('complete', (e:any) => {
      return e;
    })

    return  this.eventSource;
  }

  private closeEventSource() {
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
