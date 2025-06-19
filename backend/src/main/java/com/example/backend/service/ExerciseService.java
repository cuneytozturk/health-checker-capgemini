package com.example.backend.service;

import com.example.backend.config.exception.EntityNotFoundException;
import com.example.backend.config.exception.InvalidExerciseException;
import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final BotServiceClient botServiceClient;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ExerciseService.class);


    public ExerciseService(ExerciseRepository exerciseRepository, BotServiceClient botServiceClient) {
        this.exerciseRepository = exerciseRepository;
        this.botServiceClient = botServiceClient;
    }

    public List<Exercise> findAll() {
        logger.info("Fetching all exercises");
        return exerciseRepository.findAll();
    }

    public void save(Exercise exercise) {
        logger.info("Saving exercise: {}", exercise);
        if (exercise == null || exercise.getName() == null || exercise.getDescription() == null || exercise.getImageUrl() == null || exercise.getVideoUrl() == null) {
            throw new InvalidExerciseException("Exercise and its properties must not be null");
        }
        exerciseRepository.save(exercise);
        logger.info("Exercise saved successfully: {}", exercise);
    }

    // sends notification to the bot service url
    public void sendNotification(Long id) {
        logger.info("Sending notification for exercise with id: {}", id);
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            botServiceClient.sendNotification(exerciseOptional.get());
            logger.info("Notification sent successfully for exercise with id: {}", id);
        } else {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
    }

    public Exercise findById(Long id) {
        logger.info("Fetching exercise with id: {}", id);
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise with id " + id + " not found"));
    }

    public void deleteById(Long id) {
        logger.info("Deleting exercise with id: {}", id);
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " not found");
        }
        exerciseRepository.deleteById(id);
        logger.info("Exercise with id {} deleted successfully", id);
    }
}
