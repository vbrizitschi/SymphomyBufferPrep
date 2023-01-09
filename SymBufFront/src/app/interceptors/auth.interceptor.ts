import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {StorageService} from "../services/storage.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private storageService: StorageService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.storageService.getItem('token');

    console.log('token', token);
    console.log('request', request);
    if(!request.url.match("login")){
    request = request.clone({
      setHeaders: {
        Authorization: "Bearer " + token
      }
    });
  }
    return next.handle(request);
  }
}
