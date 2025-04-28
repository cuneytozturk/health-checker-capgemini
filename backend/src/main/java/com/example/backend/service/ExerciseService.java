package com.example.backend.service;

import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseDTO;
import com.example.backend.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final RestTemplate restTemplate;


    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
        this.restTemplate = new RestTemplate();
    }

    public void sendNotification(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();
            ExerciseDTO exerciseDTO = new ExerciseDTO(
                    exercise.getName(),
                    exercise.getDescription(),
                    exercise.getVideoUrl()
            );
            String url = "http://host.docker.internal:3978/api/notify";
            restTemplate.postForObject(url, exerciseDTO, String.class);
        } else {
            throw new IllegalArgumentException("Exercise with id " + id + " not found");
        }
    }
}
