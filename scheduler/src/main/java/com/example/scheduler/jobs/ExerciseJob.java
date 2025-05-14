package com.example.scheduler.jobs;

import org.quartz.*;
import java.time.LocalDateTime;

public class ExerciseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long exerciseId = context.getJobDetail().getJobDataMap().getLong("exerciseId");
        String notificationTimeString = context.getJobDetail().getJobDataMap().getString("time");

        System.out.println(">> Executing ExerciseJob for ID: " + exerciseId + " at " + notificationTimeString);
    }
}

