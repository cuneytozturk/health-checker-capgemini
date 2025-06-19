import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';

import { ExerciseService } from '../exercise.service';
import { EditExerciseComponent } from '../edit-exercise/edit-exercise.component';

interface Exercise {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  videoUrl: string;
  categoryId: number;
  timeRequired: number;
}

@Component({
  selector: 'app-exercise',
  standalone: true,
  imports: [RouterModule , NavbarComponent,  EditExerciseComponent],
  templateUrl: './exercise.component.html',
  styleUrl: './exercise.component.css'
})
export class ExerciseComponent {
  exercises: Exercise[] = [];
  showAddForm = false;


  constructor(private http: HttpClient, private exerciseService: ExerciseService) {}

  ngOnInit() {
    this.loadExercises();
  }

  loadExercises() {
    this.exerciseService.getWorkoutItems().subscribe(data => {
      this.exercises = data;
    });
  }

  exerciseUpdated() {
    this.showAddForm = false;
    this.loadExercises();
    this.stopEdit();
  }

  editingExerciseId: number | null = null;

  startEdit(exerciseId: number) {
    this.editingExerciseId = exerciseId;
  }

  stopEdit() {
    this.editingExerciseId = null;
  }

  deleteExercise(exerciseId: number) {
    this.exerciseService.deleteExercise(exerciseId).subscribe(() => {
      this.loadExercises();
    });
  }
}
