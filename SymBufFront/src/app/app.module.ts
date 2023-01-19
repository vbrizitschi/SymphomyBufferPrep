import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainMenuComponent } from './components/shared/main-menu/main-menu.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatToolbarModule} from "@angular/material/toolbar";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {RouterModule} from "@angular/router";
import {MatTooltipModule} from "@angular/material/tooltip";
import { LoadBufferComponent } from './components/buffer/load-buffer/load-buffer.component';
import { AppRoutingModule } from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { CalculationLogsComponent } from './components/logs/calculation-logs/calculation-logs.component';
import {MatTableModule} from "@angular/material/table";
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {JWT_OPTIONS, JwtHelperService, JwtModule} from "@auth0/angular-jwt";
import { StocklocationsComponent } from './components/refs/stocklocations/stocklocations.component';
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { LoadMinBufferComponent } from './components/buffer/load-min-buffer/load-min-buffer.component';

@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    LoadBufferComponent,
    CalculationLogsComponent,
    LoginFormComponent,
    StocklocationsComponent,
    LoadMinBufferComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MatButtonModule,
    MatSidenavModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    RouterModule,
    MatExpansionModule,
    MatTooltipModule,
    RouterModule.forRoot([]),
    AppRoutingModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatInputModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonToggleModule,
    MatSlideToggleModule,
    MatCheckboxModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  },
    {provide: JWT_OPTIONS,
      useValue: JWT_OPTIONS

    }, JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
