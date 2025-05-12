import { Routes } from '@angular/router';
import { ExerciseDetailComponent } from './exercise-detail/exercise-detail.component';
import { ExerciseComponent } from './exercise/exercise.component';

export const routes: Routes = [
{ path: 'exercises', component: ExerciseComponent },
  { path: 'exercises/:id', component: ExerciseDetailComponent },
  { path: '', redirectTo: '/exercises', pathMatch: 'full' }
];
