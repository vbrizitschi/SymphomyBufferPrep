import { Component } from '@angular/core';
import {FileService} from "../../../services/file.service";
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-load-buffer',
  templateUrl: './load-buffer.component.html',
  styleUrls: ['./load-buffer.component.css']
})
export class LoadBufferComponent {

  isLoading: boolean = false;

  status: string = "Start position";
  constructor(private fileService: FileService) {
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
}
