import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ExerciseService } from '../exercise.service';
import { FormsModule } from '@angular/forms';
import { MatTimepickerModule } from '@angular/material/timepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';



interface ExerciseSchedule {
  id: number;
  userId: number;
  exerciseId: number;
  time: string;
}

interface Exercise {
  id: number;
  name: string;
}

@Component({
  selector: 'app-exercise-schedule',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    FormsModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatTimepickerModule,
    MatIconModule],
  templateUrl: './exercise-schedule.component.html',
  styleUrls: ['./exercise-schedule.component.css']
})
export class ExerciseScheduleComponent implements OnInit {
  schedules: ExerciseSchedule[] = [];
  exerciseNames: { [id: number]: string } = {};
  userId = 101;

  newSchedule = {
      userId: this.userId,
      exerciseId: null as number | null,
      time: ''
    };

  private apiUrl = 'http://localhost:8080/api/exerciseschedule/getbyuserid/' + this.userId;
  private addUrl = 'http://localhost:8080/api/exerciseschedule/add';


  constructor(private http: HttpClient, private exerciseService: ExerciseService) {}

  ngOnInit() {
    this.loadSchedules();
  }

   loadSchedules() {
    this.http.get<ExerciseSchedule[]>(this.apiUrl).subscribe(data => {
      this.schedules = data;
      this.schedules.forEach(schedule => {
        this.exerciseService.getExerciseById(schedule.exerciseId).subscribe(exercise => {
          this.exerciseNames[exercise.id] = exercise.name;
        });
      });
    });
  }

addSchedule() {
  if (!this.newSchedule.exerciseId || !this.newSchedule.time) return;

  let timeValue: any = this.newSchedule.time;
  if (timeValue instanceof Date) {
    const hours = timeValue.getHours().toString().padStart(2, '0');
    const minutes = timeValue.getMinutes().toString().padStart(2, '0');
    timeValue = `${hours}:${minutes}`;
  }

  const payload = {
    userId: this.newSchedule.userId,
    exerciseId: this.newSchedule.exerciseId,
    time: timeValue
  };

  // todo backend should return JSON instead of text
  this.http.post(this.addUrl, payload, { responseType: 'text' }).subscribe(() => {
    this.newSchedule.exerciseId = null;
    this.newSchedule.time = '';
    this.loadSchedules();
  });
}

  getExerciseName(id: number): string {
    return this.exerciseNames[id] || 'Loading...';
  }
}