import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ExerciseService } from '../exercise.service';
import { FormsModule } from '@angular/forms';


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
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './exercise-schedule.component.html',
  styleUrls: ['./exercise-schedule.component.css']
})
export class ExerciseScheduleComponent implements OnInit {
  schedules: ExerciseSchedule[] = [];
  exerciseNames: { [id: number]: string } = {};

  newSchedule = {
      userId: 1,
      exerciseId: null as number | null,
      time: ''
    };

  private apiUrl = 'http://localhost:8080/api/exerciseschedule/getbyuserid/1';
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
    
  const today = new Date();
  const [hours, minutes] = this.newSchedule.time.split(':');
  today.setHours(Number(hours), Number(minutes), 0, 0);

  // There's a better way to format the date, but this is a "simple" solution for now
  // maybe refactor time format in database
  const pad = (n: number) => n.toString().padStart(2, '0');
  const localDateTime =
    today.getFullYear() + '-' +
    pad(today.getMonth() + 1) + '-' +
    pad(today.getDate()) + 'T' +
    pad(today.getHours()) + ':' +
    pad(today.getMinutes()) + ':' +
    pad(today.getSeconds());

  const payload = {
    userId: this.newSchedule.userId,
    exerciseId: this.newSchedule.exerciseId,
    time: localDateTime 
  };

    this.http.post(this.addUrl, payload).subscribe(() => {
      this.newSchedule.exerciseId = null;
      this.newSchedule.time = '';
      this.loadSchedules();
    });
  }

  getExerciseName(id: number): string {
    return this.exerciseNames[id] || 'Loading...';
  }
}