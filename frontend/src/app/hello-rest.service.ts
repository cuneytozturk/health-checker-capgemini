import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class HelloRestService {
  private http = inject(HttpClient);

  constructor() { }

  getHelloWorld() {
    return this.http.get('http://localhost:8080/hello');
}
}
