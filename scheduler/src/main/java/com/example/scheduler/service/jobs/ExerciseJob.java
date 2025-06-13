package com.example.scheduler.service.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExerciseJob implements Job {
    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${api.notification.url}")
    private String apiNotificationUrl;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ExerciseJob.class);

    public ExerciseJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void execute(JobExecutionContext context){
        logger.info("Executing ExerciseJob...");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        if (!jobDataMap.containsKey("exerciseId") || !jobDataMap.containsKey("time")) {
            logger.error("Exercise ID or notification time is missing.");
            return;
        }

        Long exerciseId = jobDataMap.getLong("exerciseId");
        String notificationTimeString = jobDataMap.getString("time");
        String url = apiBaseUrl + apiNotificationUrl + exerciseId;
        try {
            restTemplate.getForObject(url, String.class);
            logger.info("Notification sent for exercise ID: {} at {}", exerciseId, notificationTimeString);
        } catch (Exception e) {
            logger.error("Error sending notification for exercise ID: {}", exerciseId, e);
        }
    }
}

