package com.example.scheduler.config;

import com.example.scheduler.jobs.CheckNewScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail checkNewScheduleJobDetail() {
        return JobBuilder.newJob(CheckNewScheduleJob.class)
                .withIdentity("checkNewScheduleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger checkNewScheduleTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(checkNewScheduleJobDetail())
                .withIdentity("checkNewScheduleTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10) // Runs every 10 seconds
                        .repeatForever())
                .build();
    }
}
