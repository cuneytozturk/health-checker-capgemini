package com.example.scheduler.service.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.service.observer.ScheduleNotifier;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckNewScheduleJob implements Job {
    private final ExerciseScheduleRepository exerciseScheduleRepository;
    private final ScheduleNotifier scheduleNotifier;

    private static final Logger logger = LoggerFactory.getLogger(CheckNewScheduleJob.class);

    public CheckNewScheduleJob(ExerciseScheduleRepository exerciseScheduleRepository, ScheduleNotifier scheduleNotifier) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
        this.scheduleNotifier = scheduleNotifier;
    }

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("Executing CheckNewScheduleJob...");
        List<ExerciseSchedule> schedules = exerciseScheduleRepository.findAll();

        for (ExerciseSchedule schedule : schedules) {
            scheduleNotifier.notifyObservers(schedule);
        }
    }
}