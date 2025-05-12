package com.example.backend.service;

import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseDTO;
import com.example.backend.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final RestTemplate restTemplate;


    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public void save(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public void sendNotification(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();
            ExerciseDTO exerciseDTO = new ExerciseDTO(
                    exercise.getName(),
                    exercise.getDescription(),
                    exercise.getImageUrl(),
                    exercise.getVideoUrl()
            );
            String url = "http://host.docker.internal:3978/api/notify";
            restTemplate.postForObject(url, exerciseDTO, String.class);
        } else {
            throw new IllegalArgumentException("Exercise with id " + id + " not found");
        }
    }
}
