package com.example.scheduler.service.observer;

import com.example.scheduler.model.ExerciseSchedule;

public interface ScheduleObserver {
    void onNewSchedule(ExerciseSchedule schedule);

}
