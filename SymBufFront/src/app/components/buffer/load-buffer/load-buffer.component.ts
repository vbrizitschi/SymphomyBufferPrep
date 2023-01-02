import { Component } from '@angular/core';
import {FileService} from "../../../services/file.service";

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
}
