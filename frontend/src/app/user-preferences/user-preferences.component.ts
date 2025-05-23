import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

interface UserPreference {
  id?: number;
  userId: number;
  goalCategoryId: number | null;
  timePerDay: number | null;
  frequency: number | null;
}

@Component({
  selector: 'app-user-preferences',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './user-preferences.component.html',
  styleUrls: ['./user-preferences.component.css']
})
export class UserPreferencesComponent implements OnInit {
  preference: UserPreference | null = null;
  newPreference: UserPreference = {
    userId: 101,
    goalCategoryId: null,
    timePerDay: null,
    frequency: null
  };
  categories: { id: number; name: string; description: string }[] = [];

  private getUrl = 'http://localhost:8080/api/preferences/101';
  private addUrl = 'http://localhost:8080/api/preferences';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.loadPreference();
    this.loadCategories();
  }

  loadPreference() {
    this.http.get<UserPreference>(this.getUrl).subscribe(data => {
      if (data) {
        this.preference = data;
        // Optionally prefill the form for editing
        this.newPreference = { ...data };
      } else {
        this.preference = null;
        this.newPreference = {
          userId: 101,
          goalCategoryId: null,
          timePerDay: null,
          frequency: null
        };
      }
    });
  }

  loadCategories() {
    this.http.get<{ id: number; name: string; description: string }[]>('http://localhost:8080/api/categories').subscribe(data => {
      this.categories = data;
    });
  }

  getCategoryName(id: number | null): string {
    if (!id) return '';
    const category = this.categories.find(cat => cat.id === id);
    return category ? category.name : 'Unknown';
  }

  savePreference() {
    if (
      !this.newPreference.goalCategoryId ||
      !this.newPreference.timePerDay ||
      !this.newPreference.frequency
    ) return;

    const payload = {
      userId: 101,
      goalCategoryId: this.newPreference.goalCategoryId,
      timePerDay: this.newPreference.timePerDay,
      frequency: this.newPreference.frequency
    };

    this.http.put(this.addUrl, payload).subscribe(() => {
      this.loadPreference();
      this.router.navigate(['/exerciseschedule']);
    });
  }
}