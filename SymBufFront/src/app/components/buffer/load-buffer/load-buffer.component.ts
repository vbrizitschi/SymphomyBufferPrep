import { Component } from '@angular/core';
import {FileService} from "../../../services/file.service";

@Component({
  selector: 'app-load-buffer',
  templateUrl: './load-buffer.component.html',
  styleUrls: ['./load-buffer.component.css']
})
export class LoadBufferComponent {

  constructor(private fileService: FileService) {
  }
  upload(event:any){
    console.log(event)
    console.log(event.target?.files[0]);
    this.fileService.uploadFile(event.target.files[0]).subscribe(data => {
      console.log(data);
    })
  }
}
