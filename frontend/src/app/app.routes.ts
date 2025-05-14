import { Routes } from '@angular/router';
import { ExerciseDetailComponent } from './exercise-detail/exercise-detail.component';
import { ExerciseComponent } from './exercise/exercise.component';
import { ExerciseScheduleComponent } from './exercise-schedule/exercise-schedule.component';

export const routes: Routes = [
{ path: 'exercises', component: ExerciseComponent },
  { path: 'exercises/:id', component: ExerciseDetailComponent },
  { path: 'exerciseschedule', component: ExerciseScheduleComponent },
  { path: '', redirectTo: '/exercises', pathMatch: 'full' }
];
