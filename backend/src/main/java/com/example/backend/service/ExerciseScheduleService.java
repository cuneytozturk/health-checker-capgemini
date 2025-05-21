package com.example.backend.service;

import com.example.backend.config.exception.InvalidScheduleException;
import com.example.backend.model.ExerciseSchedule;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.slf4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseScheduleService {

    private final ExerciseScheduleRepository exerciseScheduleRepository;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ExerciseScheduleService.class);

    public ExerciseScheduleService(ExerciseScheduleRepository exerciseScheduleRepository) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    public List<ExerciseSchedule> getAllExerciseSchedules() {
        logger.info("Fetching all exercise schedules");
        return exerciseScheduleRepository.findAll();
    }

    public Optional<ExerciseSchedule> getExerciseScheduleById(Long id) {
        logger.info("Fetching exercise schedule with id: {}", id);
        return exerciseScheduleRepository.findById(id);
    }

    public List<ExerciseSchedule> getExerciseSchedulesByUserId(Long userId) {
        logger.info("Fetching exercise schedules for user with id: {}", userId);
        ExerciseSchedule example = new ExerciseSchedule();
        example.setUserId(userId);
        return exerciseScheduleRepository.findAll(Example.of(example));
    }

    public void addExerciseSchedule(ExerciseSchedule exerciseSchedule) {
        logger.info("Adding exercise schedule: {}", exerciseSchedule);
        if(exerciseSchedule.getExerciseId() == null || exerciseSchedule.getUserId() == null || exerciseSchedule.getTime() == null) {
            throw new InvalidScheduleException("Exercise ID, User ID, and Scheduled Time must not be null");
        }
        exerciseScheduleRepository.save(exerciseSchedule);
        logger.info("Exercise schedule added successfully: {}", exerciseSchedule);
    }
}