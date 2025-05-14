package com.example.scheduler.service;

import com.example.scheduler.jobs.ExerciseJob;
import com.example.scheduler.model.ExerciseSchemeEntry;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class QuartzSchedulerService {

    private final Scheduler scheduler;

    public QuartzSchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void scheduleExerciseFromScheme() throws SchedulerException {
        // Hardcoded scheme entry
        ExerciseSchemeEntry entry = new ExerciseSchemeEntry(
                "EX-123",
                java.time.LocalDateTime.now().plusSeconds(3)
        );

        scheduleExercise(entry);
    }

    public void scheduleExercise(ExerciseSchemeEntry entry) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ExerciseJob.class)
                .withIdentity("exerciseJob-" + entry.getExerciseId() + "-" + UUID.randomUUID())
                .usingJobData("exerciseId", entry.getExerciseId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + entry.getExerciseId() + "-" + UUID.randomUUID())
                .startAt(Date.from(entry.getNotificationTime().atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
