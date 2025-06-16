import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ExerciseService } from '../exercise.service';
import { SafeUrlPipe } from '../safe-url.pipe';

import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-exercise-detail',
  templateUrl: './exercise-detail.component.html',
  imports: [SafeUrlPipe, NavbarComponent],
  standalone: true,
  styleUrls: ['./exercise-detail.component.css']
})
export class ExerciseDetailComponent implements OnInit {
  exercise: any;

  constructor(
    private route: ActivatedRoute,
    private exerciseService: ExerciseService
  ) {}

  ngOnInit() {
    const exerciseId = this.route.snapshot.paramMap.get('id');
    if (exerciseId) {
      this.getExerciseDetails(Number(exerciseId));
    }
  }

  getExerciseDetails(id: number) {
    this.exerciseService.getExerciseById(id).subscribe(data => {
      this.exercise = data;
    });
  }
}