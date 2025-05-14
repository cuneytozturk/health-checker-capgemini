package com.example.scheduler.service;

import com.example.scheduler.jobs.ExerciseJob;
import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.model.ExerciseSchemeEntry;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QuartzSchedulerService {

    private final Scheduler scheduler;
    private ExerciseScheduleRepository exerciseScheduleRepository;



    public QuartzSchedulerService(Scheduler scheduler, ExerciseScheduleRepository exerciseScheduleRepository) {
        this.scheduler = scheduler;
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    @PostConstruct
    public void scheduleExerciseFromScheme() throws SchedulerException {
       scheduleExerciseForUser(1L);
    }

    public void scheduleExerciseForUser(Long userId) throws SchedulerException {
        List<ExerciseSchedule> exerciseSchedules = exerciseScheduleRepository.findByUserId(userId);

        for (ExerciseSchedule exerciseSchedule : exerciseSchedules) {
            JobDetail jobDetail = JobBuilder.newJob(ExerciseJob.class)
                    .withIdentity("exerciseJob-" + exerciseSchedule.getExerciseId() + "-" + UUID.randomUUID())
                    .usingJobData("exerciseId", exerciseSchedule.getExerciseId())
                    .usingJobData("userId", exerciseSchedule.getUserId())
                    .usingJobData("time", exerciseSchedule.getTime().toString())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger-" + exerciseSchedule.getExerciseId() + "-" + UUID.randomUUID())
                    .startAt(Date.from(exerciseSchedule.getTime().atZone(ZoneId.systemDefault()).toInstant()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
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
