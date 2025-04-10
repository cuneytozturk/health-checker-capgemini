import { Component, inject } from '@angular/core';
import { HelloRestService } from '../hello-rest.service';


@Component({
  selector: 'app-hello',
  imports: [],
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.css'
})
export class HelloComponent {
message: string = '';
helloRestService = inject(HelloRestService);
  
    constructor() { }
  
    getHelloWorld() {
      this.helloRestService.getHelloWorld().subscribe((data: any) => {
        this.message = data.message;
      });
}
}
