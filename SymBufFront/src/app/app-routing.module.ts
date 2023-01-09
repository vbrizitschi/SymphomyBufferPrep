import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoadBufferComponent} from "./components/buffer/load-buffer/load-buffer.component";
import {RouterModule, Routes} from "@angular/router";
import {CalculationLogsComponent} from "./components/logs/calculation-logs/calculation-logs.component";
import {LoginFormComponent} from "./components/auth/login-form/login-form.component";
import {AuthGuard} from "./components/auth/auth.guard";

const routes: Routes = [
  { path: 'load-buffer', component: LoadBufferComponent, canActivate: [AuthGuard] },
  { path: 'calculation-logs', component: CalculationLogsComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginFormComponent}
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
