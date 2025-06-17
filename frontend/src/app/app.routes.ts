import { Routes } from '@angular/router';
import { ExerciseDetailComponent } from './exercise-detail/exercise-detail.component';
import { ExerciseComponent } from './exercise/exercise.component';
import { ExerciseSchedulePageComponent } from './exercise-schedule-page/exercise-schedule-page.component';
import { UserPreferencesComponent } from './user-preferences/user-preferences.component';

export const routes: Routes = [
{ path: 'exercises', component: ExerciseComponent },
  { path: 'exercises/:id', component: ExerciseDetailComponent },
  { path: 'exerciseschedule', component: ExerciseSchedulePageComponent },
  { path: 'userpreferences', component: UserPreferencesComponent },
  { path: '', redirectTo: '/userpreferences', pathMatch: 'full' }
];
