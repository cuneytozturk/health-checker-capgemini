import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { EventEmitter, Output } from '@angular/core';

import { ExerciseService } from '../exercise.service';

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
  selector: 'app-edit-exercise',
  standalone: true,
  templateUrl: './edit-exercise.component.html',
  styleUrl: './edit-exercise.component.css',
  imports: [FormsModule]
})

export class EditExerciseComponent {
  formSubmitted = false;

  constructor(private exerciseService: ExerciseService) {}

  newExercise: Omit<Exercise, 'id'> = {
    name: '',
    description: '',
    imageUrl: '',
    videoUrl: '',
    categoryId: null as any,
    timeRequired: null as any
  };

@Output() exerciseAdded = new EventEmitter<void>();

addExercise(form: NgForm) {
    this.formSubmitted = true;
    if (form.invalid) {
      return;
    }
    this.exerciseService.addExercise(this.newExercise).subscribe({
      next: () => {
        this.resetForm();
        this.exerciseAdded.emit();
      }
    });
  }

  resetForm() {
    this.newExercise = {
      name: '',
      description: '',
      imageUrl: '',
      videoUrl: '',
      categoryId: null as any,
      timeRequired: null as any
    };
  }
}
