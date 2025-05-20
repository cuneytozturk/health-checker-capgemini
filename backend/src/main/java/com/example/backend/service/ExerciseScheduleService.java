package com.example.backend.service;

import com.example.backend.model.ExerciseSchedule;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseScheduleService {

    private final ExerciseScheduleRepository exerciseScheduleRepository;

    public ExerciseScheduleService(ExerciseScheduleRepository exerciseScheduleRepository) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    public List<ExerciseSchedule> getAllExerciseSchedules() {
        return exerciseScheduleRepository.findAll();
    }

    public Optional<ExerciseSchedule> getExerciseScheduleById(Long id) {
        return exerciseScheduleRepository.findById(id);
    }

    public List<ExerciseSchedule> getExerciseSchedulesByUserId(Long userId) {
        ExerciseSchedule example = new ExerciseSchedule();
        example.setUserId(userId);
        return exerciseScheduleRepository.findAll(Example.of(example));
    }

    public void addExerciseSchedule(ExerciseSchedule exerciseSchedule) {
        exerciseScheduleRepository.save(exerciseSchedule);
    }
}