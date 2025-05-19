package com.example.scheduler.jobs;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.repository.ExerciseScheduleRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CheckNewScheduleJob implements Job {
    private ExerciseScheduleRepository exerciseScheduleRepository;

    public CheckNewScheduleJob(ExerciseScheduleRepository exerciseScheduleRepository) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(">> Executing CheckNewScheduleJob");
        Scheduler scheduler = context.getScheduler();
        List<ExerciseSchedule> entries = exerciseScheduleRepository.findAll();

        for (ExerciseSchedule entry : entries) {
            JobKey jobKey = new JobKey("job_" + entry.getId());

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
                            .withIdentity("trigger_" + entry.getExerciseId() + "-" + UUID.randomUUID())
                            .withSchedule(CronScheduleBuilder.cronSchedule(cronexpression))
                            .build();

                    scheduler.scheduleJob(jobDetail, trigger);
                }
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
