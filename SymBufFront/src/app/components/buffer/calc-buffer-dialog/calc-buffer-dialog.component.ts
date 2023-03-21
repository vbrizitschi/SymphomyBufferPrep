import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {FileService} from "../../../services/file.service";

@Component({
  selector: 'app-calc-buffer-dialog',
  templateUrl: './calc-buffer-dialog.component.html',
  styleUrls: ['./calc-buffer-dialog.component.css']
})
export class CalcBufferDialogComponent  implements OnInit{

  status: string[] = [];

  isLoading: boolean = false;

  id: any;
  constructor(public dialog: MatDialog,private fileService: FileService,
              @Inject(MAT_DIALOG_DATA) public dialogData: any) {}

 doCalc(){
    console.log('dialog ', this.dialogData.calcParam)
      this.fileService.calculateBuffer(this.dialogData.calcParam).subscribe(data =>{
         console.log('data ', data);
        this.isLoading = true;
          this.status.push(data)

      }, error => {
        console.error('error',error);
        this.status.push(error)

        this.isLoading = false;
        }, ()=>{
        this.isLoading = false;
      })
 }

  ngOnInit(): void {
    this.doCalc();
  }

}
