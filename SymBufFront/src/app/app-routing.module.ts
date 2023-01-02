import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoadBufferComponent} from "./components/buffer/load-buffer/load-buffer.component";
import {RouterModule, Routes} from "@angular/router";
import {CalculationLogsComponent} from "./components/logs/calculation-logs/calculation-logs.component";

const routes: Routes = [
  { path: 'load-buffer', component: LoadBufferComponent },
  { path: 'calculation-logs', component: CalculationLogsComponent}
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
