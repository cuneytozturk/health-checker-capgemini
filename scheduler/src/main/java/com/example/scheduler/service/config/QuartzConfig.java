package com.example.scheduler.service.config;

import com.example.scheduler.service.jobs.CheckNewScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
        final int intervalInSeconds = 60;
        return TriggerBuilder.newTrigger()
                .forJob(checkNewScheduleJobDetail())
                .withIdentity("checkNewScheduleTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(intervalInSeconds)
                        .repeatForever())
                .build();
    }
}
