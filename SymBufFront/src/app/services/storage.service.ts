import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  addItem(key: string, value: any) {
    window.localStorage.setItem(key, value);
  }

  getItem(key: string) {
    return window.localStorage.getItem(key);
  }

  clean() {
    window.localStorage.clear();
  }
}
