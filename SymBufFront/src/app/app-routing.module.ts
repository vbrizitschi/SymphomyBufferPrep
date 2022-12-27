import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoadBufferComponent} from "./components/buffer/load-buffer/load-buffer.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  { path: 'load-buffer', component: LoadBufferComponent }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports:[
    RouterModule
  ]
})
export class AppRoutingModule { }
