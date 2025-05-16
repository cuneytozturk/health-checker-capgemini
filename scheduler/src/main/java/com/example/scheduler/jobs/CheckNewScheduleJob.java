package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CheckNewScheduleJob implements Job {

    private ExerciseScheduleRepository exerciseScheduleRepository;

    public CheckNewScheduleJob(ExerciseScheduleRepository exerciseScheduleRepository){
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    private static LocalDateTime lastChecked = LocalDateTime.now();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<ExerciseSchedule> newSchedules = exerciseScheduleRepository.findByCreatedAtAfter(lastChecked);
        if (!newSchedules.isEmpty()) {
            System.out.println("New ExerciseSchedules found: " + newSchedules);
        }

        lastChecked = LocalDateTime.now();
    }
}
