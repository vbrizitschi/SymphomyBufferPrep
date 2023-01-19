import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoadBufferComponent} from "./components/buffer/load-buffer/load-buffer.component";
import {RouterModule, Routes} from "@angular/router";
import {CalculationLogsComponent} from "./components/logs/calculation-logs/calculation-logs.component";
import {LoginFormComponent} from "./components/auth/login-form/login-form.component";
import {AuthGuard} from "./components/auth/auth.guard";
import {StocklocationsComponent} from "./components/refs/stocklocations/stocklocations.component";
import {LoadMinBufferComponent} from "./components/buffer/load-min-buffer/load-min-buffer.component";

const routes: Routes = [
  { path: 'load-buffer', component: LoadBufferComponent, canActivate: [AuthGuard] },
  { path: 'load-min-buffer', component: LoadMinBufferComponent, canActivate: [AuthGuard]},
  { path: 'calculation-logs', component: CalculationLogsComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginFormComponent},
  { path: 'stock-locations', component: StocklocationsComponent}
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
