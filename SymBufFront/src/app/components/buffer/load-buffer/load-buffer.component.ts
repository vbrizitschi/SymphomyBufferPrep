import {Component, NgZone, OnDestroy, OnInit} from '@angular/core';
import {FileService} from "../../../services/file.service";
import {saveAs} from 'file-saver';
import {BehaviorSubject, Observable, Subject, switchMap, takeUntil, timer} from "rxjs";


@Component({
  selector: 'app-load-buffer',
  templateUrl: './load-buffer.component.html',
  styleUrls: ['./load-buffer.component.css']
})
export class LoadBufferComponent implements OnInit{

  event: Observable<any>;
  private _eventSource: EventSource;
  private _events: BehaviorSubject<any> = new BehaviorSubject<any>(null);


  isLoading: boolean = false;

  status: string = "";
  constructor(private fileService: FileService,
              private _zone: NgZone) {
    this._eventSource = this.createEventSource();
    this.event = this.createEventObservable();
  }

  upload(event:any){
    this.isLoading = true;
    this.fileService.uploadFile(event.target.files[0]).subscribe(data => {
      console.log(data);
      this.status = data;
     this.isLoading = false;
    }, error => {
      this.isLoading = false;
    })

  }

  downloadFile() {
      this.fileService.downloadFile('load_buffer.xlsx').subscribe(data => {
        console.log('data', data)
        this.saveToFileSystem(data, 'buffer-template.xlsx');
      })
  }

  private saveToFileSystem(response: any, docName: string) {
    const blob = new Blob([response]);
    saveAs(blob, docName);
  }


  private createEventObservable(): Observable<any> {
    return this._events.asObservable();
  }

  private createEventSource(): EventSource {
    const eventSource = new EventSource('http://localhost:8080/sse/words');
    eventSource.onmessage = sse => {
      console.log('sse', sse)
      const event: string = sse.data;
      this.status += '\n' + sse.data;
      this._zone.run(()=>this._events.next(event));
    };
    eventSource.onerror = err => this._events.error(err);
    return eventSource;
  }

  ngOnInit(): void {
  }

}
