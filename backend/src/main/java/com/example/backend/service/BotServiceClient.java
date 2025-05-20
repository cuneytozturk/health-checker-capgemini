package com.example.backend.service;

import com.example.backend.model.Exercise;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BotServiceClient {

    private final RestTemplate restTemplate;

    public BotServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(String url, Exercise exercise) {
        restTemplate.postForObject(url, exercise, String.class);
    }
}