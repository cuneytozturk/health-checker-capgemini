import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ExerciseService } from '../exercise.service';

@Component({
  selector: 'app-edit-exercise',
  templateUrl: './edit-exercise.component.html',
  styleUrls: ['./edit-exercise.component.css'],
  standalone: true,
  imports: [FormsModule]
})
export class EditExerciseComponent implements OnChanges {
  @Input() exercise: any; // Replace 'any' with your Exercise interface
  @Output() exerciseAdded = new EventEmitter<void>();
  @Output() exerciseUpdated = new EventEmitter<void>();
  @Output() editCancelled = new EventEmitter<void>();

  formModel = {
    name: '',
    description: '',
    imageUrl: '',
    videoUrl: '',
    categoryId: 0,
    timeRequired: 0
  };

  editingId: number | null = null;
  formSubmitted = false;

  constructor(private exerciseService: ExerciseService) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['exercise'] && this.exercise) {
      this.editingId = this.exercise.id;
      this.formModel = {
        name: this.exercise.name || '',
        description: this.exercise.description || '',
        imageUrl: this.exercise.imageUrl || '',
        videoUrl: this.exercise.videoUrl || '',
        categoryId: this.exercise.categoryId || 0,
        timeRequired: this.exercise.timeRequired || 0
      };
    } else if (!this.exercise) {
      this.editingId = null;
      this.resetForm();
    }
  }

  onSubmit(form: NgForm) {
    this.formSubmitted = true;
    if (form.invalid) {
      return;
    }
    if (this.editingId) {
      this.updateExercise();
    } else {
      this.addExercise();
    }
  }

  addExercise() {
    this.exerciseService.editExercise(this.formModel).subscribe({
      next: () => {
        this.exerciseAdded.emit();
        this.resetForm();
      },
      error: () => {
        // handle error
      }
    });
  }

  updateExercise() {
    const payload = { ...this.formModel, id: this.editingId };
    this.exerciseService.editExercise(payload).subscribe({
      next: () => {
        this.exerciseUpdated.emit();
        this.resetForm();
      },
      error: () => {
        // handle error
      }
    });
  }

  resetForm() {
    this.formModel = {
      name: '',
      description: '',
      imageUrl: '',
      videoUrl: '',
      categoryId: 0,
      timeRequired: 0
    };
    this.editingId = null;
  }

  cancelEdit() {
    this.editCancelled.emit();
    this.resetForm();
  }
}