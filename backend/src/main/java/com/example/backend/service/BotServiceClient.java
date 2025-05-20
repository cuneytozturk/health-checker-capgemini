package com.example.backend.service;

import com.example.backend.model.Exercise;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BotServiceClient {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(BotServiceClient.class);

    @Value("${api.botservice.url}")
    private String botServiceUrl;

    private final RestTemplate restTemplate;

    public BotServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(Exercise exercise) {
        logger.info("Sending notification to bot service for exercise: {}", exercise);
        restTemplate.postForObject(botServiceUrl, exercise, String.class);
    }
}