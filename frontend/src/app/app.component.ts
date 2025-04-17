import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HelloComponent } from './hello/hello.component';
import { ExerciseComponent } from "./exercise/exercise.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HelloComponent, ExerciseComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
