import { Component } from '@angular/core';
import {LoginService} from "../../../services/login.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent {

  constructor(private loginService: LoginService,
              private router: Router) {
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['/']);
  }
}
