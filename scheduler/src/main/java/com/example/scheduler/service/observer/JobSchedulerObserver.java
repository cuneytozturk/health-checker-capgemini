package com.example.scheduler.service.observer;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.service.jobs.ExerciseJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

@Component
public class JobSchedulerObserver implements ScheduleObserver {
    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(JobSchedulerObserver.class);
    private static final String JOB_PREFIX = "job_";
    private static final String TRIGGER_PREFIX = "trigger_";

    public JobSchedulerObserver(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void onNewSchedule(ExerciseSchedule schedule) {
        try {
            JobKey jobKey = new JobKey(JOB_PREFIX + schedule.getId());
            if (!scheduler.checkExists(jobKey)) {
                LocalTime exerciseTime = schedule.getTime();
                int hour = exerciseTime.getHour();
                int minute = exerciseTime.getMinute();
                String cronExpression = String.format("0 %d %d * * ?", minute, hour);

                JobDetail jobDetail = JobBuilder.newJob(ExerciseJob.class)
                        .withIdentity(jobKey)
                        .usingJobData("exerciseId", schedule.getExerciseId())
                        .usingJobData("time", schedule.getTime().toString())
                        .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(TRIGGER_PREFIX + schedule.getExerciseId() + "-" + UUID.randomUUID())
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
                logger.info("Scheduled job for exercise ID: " + schedule.getExerciseId() + " at " + schedule.getTime());
            }
        } catch (SchedulerException e) {
            logger.error("Error scheduling job for exercise ID: " + schedule.getExerciseId(), e);
        }
    }
}