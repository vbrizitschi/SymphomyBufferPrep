import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {LoginService} from "../../../services/login.service";

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

  constructor(private loginService: LoginService) {
  }

  submit() {
    if (this.form.valid) {
      // this.submitEM.emit(this.form.value);
      this.loginService.login({login: this.form.get('username')?.value, password: this.form.get('password')?.value}).subscribe(data => {
        console.log('Auth result: ', data);
      })
    }
  }
  @Input() error: string | null = '';

  @Output() submitEM = new EventEmitter();
}
