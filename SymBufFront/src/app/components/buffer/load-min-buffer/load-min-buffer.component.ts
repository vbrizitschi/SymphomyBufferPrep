import { Component } from '@angular/core';
import {saveAs} from "file-saver";
import {FileService} from "../../../services/file.service";

@Component({
  selector: 'app-load-min-buffer',
  templateUrl: './load-min-buffer.component.html',
  styleUrls: ['./load-min-buffer.component.css']
})
export class LoadMinBufferComponent {
  isLoading: boolean = false;

  status: string = "";

  constructor(private fileService: FileService) {
  }

  upload(event:any){
    this.isLoading = true;
    this.fileService.uploadMinBuffers(event.target.files[0]).subscribe(data => {
      console.log(data);
      this.status = data;
      this.isLoading = false;
    }, error => {
      this.isLoading = false;
    })

  }

  downloadFile() {
    this.fileService.downloadFile('load_min_buffer.xlsx').subscribe(data => {
      console.log('data', data)
      this.saveToFileSystem(data, 'buffer-template.xlsx');
    })
  }

  private saveToFileSystem(response: any, docName: string) {
    const blob = new Blob([response]);
    saveAs(blob, docName);
  }

}
