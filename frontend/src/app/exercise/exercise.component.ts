import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ExerciseService } from '../exercise.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-exercise',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './exercise.component.html',
  styleUrl: './exercise.component.css'
})
export class ExerciseComponent {
  exercises: any[] = [];

  constructor(private http: HttpClient, private exerciseService: ExerciseService) {}

  ngOnInit() {
    this.getWorkouts();
  }

  getWorkouts() {
    this.exerciseService.getWorkoutItems().subscribe(data => {
      this.exercises = data;
    });
  }
}
