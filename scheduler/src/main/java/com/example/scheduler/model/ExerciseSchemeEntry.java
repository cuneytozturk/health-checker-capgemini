package com.example.scheduler.model;

import java.time.LocalDateTime;

public class ExerciseSchemeEntry {
    private String exerciseId;
    private LocalDateTime notificationTime;

    public ExerciseSchemeEntry(String exerciseId, LocalDateTime notificationTime) {
        this.exerciseId = exerciseId;
        this.notificationTime = notificationTime;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }
}

