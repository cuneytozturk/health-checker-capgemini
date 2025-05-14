package com.example.scheduler.jobs;

import org.quartz.*;
import java.time.LocalDateTime;

public class ExerciseJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String exerciseId = context.getJobDetail().getJobDataMap().getString("exerciseId");
        System.out.println(">> Executing ExerciseJob for ID: " + exerciseId + " at " + LocalDateTime.now());
    }
}

