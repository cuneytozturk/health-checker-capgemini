import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  private apiUrl = 'http://localhost:8080/api/exercises/';

  constructor(private http: HttpClient) { }

  getWorkoutItems(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}getall`);
  }

  getExerciseById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}get/${id}`);
  }

  editExercise(payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}add`, payload, { responseType: 'text' });
  }

  deleteExercise(exerciseId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}delete/${exerciseId}`, { responseType: 'text' });
  }

}

