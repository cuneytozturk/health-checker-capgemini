package com.example.backend.service;

import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseDTO;
import com.example.backend.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final BotServiceClient botServiceClient;

    @Value("${api.botservice.url}")
    private String botServiceUrl;


    public ExerciseService(ExerciseRepository exerciseRepository, BotServiceClient botServiceClient) {
        this.exerciseRepository = exerciseRepository;
        this.botServiceClient = botServiceClient;
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public void save(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    // sends notification to the bot service url
    public void sendNotification(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            botServiceClient.sendNotification(botServiceUrl, exerciseOptional.get());
        } else {
            throw new IllegalArgumentException("Exercise with id " + id + " not found");
        }
    }

    public Exercise findById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise with id " + id + " not found"));
    }
}
