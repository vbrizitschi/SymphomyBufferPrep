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
      headers: request.headers
        // .append('Access-Control-Expose-Headers', 'Authorization')
       .append('Authorization', 'Bearer ' + token)
       // .append("Access-Control-Allow-Origin", "http://localhost:4200/")
       // .append("Access-Control-Allow-Methods", "*")
       // .append("Access-Control-Allow-Headers", "Accept,Accept-Charset,Accept-Encoding,Accept-Language,Authorization,Connection,Content-Type,Cookie,DNT,Host,Keep-Alive,Origin,Referer,User-Agent,X-CSRF-Token,X-Requested-With")
    });
  }
    return next.handle(request);
  }

  authHeaders(token: string) :Headers {

    let headers: Headers = new Headers();
    headers.append('Access-Control-Expose-Headers', 'Authorization');
    headers.append('Authorization', 'Bearer ' + token);
    headers.append("Access-Control-Allow-Origin", "http://localhost:4200/");
    headers.append("Access-Control-Allow-Methods", "*");
    headers.append("Access-Control-Allow-Headers", "Accept,Accept-Charset,Accept-Encoding,Accept-Language,Authorization,Connection,Content-Type,Cookie,DNT,Host,Keep-Alive,Origin,Referer,User-Agent,X-CSRF-Token,X-Requested-With");
    headers.append("Access-Control-Allow-Credentials", "true");

    return headers;
  }

}
