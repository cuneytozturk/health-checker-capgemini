import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { take } from 'rxjs/operators';
import { NavbarComponent } from '../navbar/navbar.component';

const PREFERENCE_URL = 'http://localhost:8080/api/preferences/101';
const ADD_PREFERENCE_URL = 'http://localhost:8080/api/preferences';
const CATEGORIES_URL = 'http://localhost:8080/api/categories';

interface UserPreference {
  id?: number;
  userId: number;
  goalCategoryId: number | null;
  timePerDay: number | null;
  frequency: number | null;
}

interface Category {
  id: number;
  name: string;
  description: string;
}

@Component({
  selector: 'app-user-preferences',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
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
  categories: Category[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.loadPreference();
    this.loadCategories();
  }

  loadPreference() {
    this.http.get<UserPreference>(PREFERENCE_URL).pipe(take(1)).subscribe({
      next: data => {
        if (data) {
          this.preference = data;
          this.newPreference = { ...data };
        } else {
          this.preference = null;
          this.resetNewPreference();
        }
      },
      error: err => {
        // handle error (show message, etc.)
        this.preference = null;
        this.resetNewPreference();
      }
    });
  }

  loadCategories() {
    this.http.get<Category[]>(CATEGORIES_URL).pipe(take(1)).subscribe({
      next: data => this.categories = data,
      error: err => this.categories = []
    });
  }

  getCategoryName(id: number | null): string {
    if (!id) return '';
    const category = this.categories.find(cat => cat.id === id);
    return category ? category.name : 'Unknown';
  }

  isFormValid(): boolean {
    const { goalCategoryId, timePerDay, frequency } = this.newPreference;
    return !!(goalCategoryId && timePerDay && frequency);
  }

  savePreference() {
    if (!this.isFormValid()) return;

    const payload = {
      userId: 101,
      goalCategoryId: this.newPreference.goalCategoryId,
      timePerDay: this.newPreference.timePerDay,
      frequency: this.newPreference.frequency
    };

    this.http.put(ADD_PREFERENCE_URL, payload).pipe(take(1)).subscribe({
      next: () => {
        this.loadPreference();
        this.router.navigate(['/exerciseschedule']);
      },
      error: err => {
        // handle error (show message, etc.)
      }
    });
  }

  private resetNewPreference() {
    this.newPreference = {
      userId: 101,
      goalCategoryId: null,
      timePerDay: null,
      frequency: null
    };
  }
}