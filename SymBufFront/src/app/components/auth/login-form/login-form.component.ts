import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {LoginService} from "../../../services/login.service";
import {StorageService} from "../../../services/storage.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  returnUrl: string = '';
  error: string = '';

  constructor(private loginService: LoginService,
              private storageService: StorageService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  submit() {
    if (this.form.valid) {
      this.loginService.login({login: this.form.get('username')?.value, password: this.form.get('password')?.value}).subscribe(data => {
        this.storageService.addItem('token', data.jwtToken);
        this.route.queryParams.subscribe(params => {
          this.returnUrl = params['returnUrl'];
        })
        this.router.navigate([this.returnUrl]);
      }, err => {
        console.error('Login error: ', err)
        this.error = err;
      })
    }
  }
}
