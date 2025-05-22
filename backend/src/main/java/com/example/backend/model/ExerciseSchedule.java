package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class ExerciseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long exerciseId;

    private LocalTime time; // Changed from LocalDateTime to LocalTime

    public ExerciseSchedule() {
    }

    public ExerciseSchedule(Long id, Long userId, Long exerciseId, LocalTime time) {
        this.id = id;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.time = time;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}