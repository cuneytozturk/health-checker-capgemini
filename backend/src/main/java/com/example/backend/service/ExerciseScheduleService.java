package com.example.backend.service;

import com.example.backend.config.exception.InvalidScheduleException;
import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseSchedule;
import com.example.backend.model.Preferences;
import com.example.backend.repository.ExerciseRepository;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.slf4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseScheduleService {

    private final ExerciseScheduleRepository exerciseScheduleRepository;
    private final ExerciseRepository exerciseRepository;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ExerciseScheduleService.class);

    public ExerciseScheduleService(ExerciseScheduleRepository exerciseScheduleRepository, ExerciseRepository exerciseRepository) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
        this.exerciseRepository = exerciseRepository;
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

    public void deleteSchedulesByUserId(Long userId) {
        logger.info("Deleting exercise schedules for user with id: {}", userId);
        List<ExerciseSchedule> schedules = getExerciseSchedulesByUserId(userId);
        if (!schedules.isEmpty()) {
            exerciseScheduleRepository.deleteAll(schedules);
            logger.info("Deleted {} exercise schedules for user with id: {}", schedules.size(), userId);
        } else {
            logger.warn("No exercise schedules found for user with id: {}", userId);
        }
    }

    public void addExerciseSchedule(ExerciseSchedule exerciseSchedule) {
        logger.info("Adding exercise schedule: {}", exerciseSchedule);
        if(exerciseSchedule.getExerciseId() == null || exerciseSchedule.getUserId() == null || exerciseSchedule.getTime() == null) {
            throw new InvalidScheduleException("Exercise ID, User ID, and Time must not be null");
        }
        exerciseScheduleRepository.save(exerciseSchedule);
        logger.info("Exercise schedule added successfully: {}", exerciseSchedule);
    }

    public void createDailyExerciseSchedules(Preferences preferences) {
        logger.info("Creating daily exercise schedules for user: {}", preferences.getUserId());

        List<Exercise> exercises = exerciseRepository.findByCategoryId(preferences.getGoalCategoryId());
        if (exercises.isEmpty()) {
            logger.warn("No exercises found for category ID: {}", preferences.getGoalCategoryId());
            return;
        }

        int sessionTime = preferences.getTimePerDay() / preferences.getFrequency();
        int frequency = preferences.getFrequency();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        int intervalMinutes = (int) java.time.Duration.between(startTime, endTime).toMinutes() / frequency;

        List<ExerciseSchedule> schedules = new ArrayList<>();
        int exerciseIndex = 0;

        for (int session = 0; session < frequency; session++) {
            LocalTime sessionStartTime = startTime.plusMinutes(session * intervalMinutes);
            logger.info("Scheduling session {} at time: {}", session + 1, sessionStartTime);

            int remainingSessionTime = sessionTime;
            while (remainingSessionTime > 0) {
                Exercise exercise = exercises.get(exerciseIndex);

                if (exercise.getTimeRequired() <= remainingSessionTime) {
                    ExerciseSchedule schedule = new ExerciseSchedule();
                    schedule.setUserId(preferences.getUserId());
                    schedule.setExerciseId(exercise.getId());
                    schedule.setTime(sessionStartTime); // Use LocalTime

                    schedules.add(schedule);
                    remainingSessionTime -= exercise.getTimeRequired();
                    sessionStartTime = sessionStartTime.plusMinutes(exercise.getTimeRequired());

                    logger.info("Scheduled exercise: {} for user: {} at time: {}", exercise.getName(), preferences.getUserId(), schedule.getTime());
                } else {
                    remainingSessionTime = 0;
                }

                exerciseIndex = (exerciseIndex + 1) % exercises.size();
            }
        }

        exerciseScheduleRepository.saveAll(schedules);
        logger.info("Daily exercise schedules created successfully for user: {}", preferences.getUserId());
    }
}