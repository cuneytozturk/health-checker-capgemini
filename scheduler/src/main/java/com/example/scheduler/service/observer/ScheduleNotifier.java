package com.example.scheduler.service.observer;

import com.example.scheduler.model.ExerciseSchedule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleNotifier {
    private final List<ScheduleObserver> observers = new ArrayList<>();

    public void addObserver(ScheduleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScheduleObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(ExerciseSchedule schedule) {
        for (ScheduleObserver observer : observers) {
            observer.onNewSchedule(schedule);
        }
    }
}