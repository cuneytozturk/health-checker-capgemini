import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { ExerciseScheduleComponent } from '../exercise-schedule/exercise-schedule.component';
import { HttpClient } from '@angular/common/http';
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

@Component({
  selector: 'app-exercise-schedule-page',
  standalone: true,
  templateUrl: './exercise-schedule-page.component.html',
  styleUrl: './exercise-schedule-page.component.css',
  imports: [NavbarComponent, ExerciseScheduleComponent, FormsModule, MatTimepickerModule, MatFormFieldModule, MatInputModule, MatIconModule]
})
export class ExerciseSchedulePageComponent implements OnInit {
  schedules: ExerciseSchedule[] = [];
  userId = 101;
  newSchedule = {
    userId: this.userId,
    exerciseId: null as number | null,
    time: ''
  };

  private apiUrl = 'http://localhost:8080/api/exerciseschedule/getbyuserid/' + this.userId;
  private addUrl = 'http://localhost:8080/api/exerciseschedule/add';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadSchedules();
  }

  loadSchedules() {
    this.http.get<ExerciseSchedule[]>(this.apiUrl).subscribe(data => {
      this.schedules = data;
    });
  }

  addSchedule() {
    if (!this.newSchedule.exerciseId || !this.newSchedule.time) return;

    let timeValue: any = this.newSchedule.time;
    if (timeValue instanceof Date) {
      // format as needed
    }

    const payload = {
      userId: this.newSchedule.userId,
      exerciseId: this.newSchedule.exerciseId,
      time: timeValue
    };

    this.http.post(this.addUrl, payload).subscribe(() => {
      this.newSchedule.exerciseId = null;
      this.newSchedule.time = '';
      this.loadSchedules();
    });
  }
}