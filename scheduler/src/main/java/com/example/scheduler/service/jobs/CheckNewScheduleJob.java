package com.example.scheduler.service.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CheckNewScheduleJob implements Job {
    private final ExerciseScheduleRepository exerciseScheduleRepository;
    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(CheckNewScheduleJob.class);
    private static final String JOB_PREFIX = "job_";
    private static final String TRIGGER_PREFIX = "trigger_";

    public CheckNewScheduleJob(ExerciseScheduleRepository exerciseScheduleRepository, Scheduler scheduler) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
        this.scheduler = scheduler;
    }

    @Override
    public void execute(JobExecutionContext context){
        logger.info("Executing CheckNewScheduleJob...");
        List<ExerciseSchedule> entries = exerciseScheduleRepository.findAll();

        for (ExerciseSchedule entry : entries) {
            JobKey jobKey = new JobKey(JOB_PREFIX + entry.getId());

            try {
                if (!scheduler.checkExists(jobKey)) {
                    LocalDateTime exerciseTime = entry.getTime();
                    int hour = exerciseTime.getHour();
                    int minute = exerciseTime.getMinute();
                    String cronexpression = String.format("0 %d %d * * ?", minute, hour);

                    JobDetail jobDetail = JobBuilder.newJob(ExerciseJob.class)
                            .withIdentity(jobKey)
                            .usingJobData("exerciseId", entry.getExerciseId())
                            .usingJobData("time", entry.getTime().toString())
                            .build();

                    Trigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity(TRIGGER_PREFIX + entry.getExerciseId() + "-" + UUID.randomUUID())
                            .withSchedule(CronScheduleBuilder.cronSchedule(cronexpression))
                            .build();

                    scheduler.scheduleJob(jobDetail, trigger);
                    logger.info("Scheduled job for exercise ID: " + entry.getExerciseId() + " at " + entry.getTime());
                }
            } catch (SchedulerException e) {
                logger.error("Error scheduling job for exercise ID: " + entry.getExerciseId(), e);
            }
        }
    }
}
