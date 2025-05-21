package com.example.backend.service;

import com.example.backend.model.Exercise;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BotServiceClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final Logger logger = mock(Logger.class);
    private final BotServiceClient botServiceClient = new BotServiceClient(restTemplate);

    @Value("${api.botservice.url}")
    private String botServiceUrl;


    @Test
    void sendNotificationCallsRestTemplateWithCorrectParameters() {
        // Arrange
        Exercise exercise = new Exercise();
        exercise.setName("Push Up");
        exercise.setDescription("A basic push-up exercise");

        // Act
        botServiceClient.sendNotification(exercise);

        // Assert
        verify(restTemplate, times(1)).postForObject(botServiceUrl, exercise, String.class);
    }
}