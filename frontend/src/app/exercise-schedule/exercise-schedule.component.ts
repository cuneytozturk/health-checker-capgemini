import { Component, Input, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExerciseService } from '../exercise.service';

interface ExerciseSchedule {
  id: number;
  userId: number;
  exerciseId: number;
  time: string;
}

interface Exercise {
  id: number;
  name: string;
  // ...other fields
}

@Component({
  selector: 'app-exercise-schedule',
  standalone: true,
  templateUrl: './exercise-schedule.component.html',
  styleUrls: ['./exercise-schedule.component.css'],
  imports: [RouterLink]
})
export class ExerciseScheduleComponent implements OnInit {
  @Input() schedules: ExerciseSchedule[] = [];
  private exerciseMap = new Map<number, Exercise>();

  constructor(private exerciseService: ExerciseService) {}

  ngOnInit() {
    this.exerciseService.getWorkoutItems().subscribe((exercises: Exercise[]) => {
      for (const ex of exercises) {
        this.exerciseMap.set(ex.id, ex);
      }
    });
  }

  getExerciseName(exerciseId: number): string {
    return this.exerciseMap.get(exerciseId)?.name || '';
  }
}