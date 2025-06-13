package com.example.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExerciseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long exerciseId;

    private LocalTime time; // Changed from LocalDateTime to LocalTime

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public ExerciseSchedule() {
    }

    public ExerciseSchedule(Long id, Long userId, Long exerciseId, LocalTime time, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.time = time;
        this.createdDate = createdDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}