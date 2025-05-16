package com.example.scheduler.jobs;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
public class ExerciseJob implements Job {

    @Value("${api.base.url}")
    private String apiBaseUrl;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        Long exerciseId = context.getJobDetail().getJobDataMap().getLong("exerciseId");
        String notificationTimeString = context.getJobDetail().getJobDataMap().getString("time");
        System.out.println(">> Executing ExerciseJob for ID: " + exerciseId + " at " + notificationTimeString);

        String url = apiBaseUrl + "/api/exercises/sendnotification/" + exerciseId;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(url, String.class);
            System.out.println("Notification sent successfully for ID: " + exerciseId);
        } catch (Exception e) {
            System.err.println("Failed to send notification for ID: " + exerciseId);
            e.printStackTrace();
        }


    }
}

