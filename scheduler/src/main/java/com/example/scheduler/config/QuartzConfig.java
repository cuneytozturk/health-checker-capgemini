package com.example.scheduler.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

//    @Bean
//    public JobDetail sampleJobDetail() {
//        return JobBuilder.newJob(SampleJob.class)
//                .withIdentity("sampleJob")
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger sampleJobTrigger() {
//        System.out.println("Trigger created"); // Add this
//
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10) // every 10 seconds
//                .repeatForever();
//
//        return TriggerBuilder.newTrigger()
//                .forJob(sampleJobDetail())
//                .withIdentity("sampleTrigger")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
}
