package com.example.backend.service;

import com.example.backend.config.exception.EntityNotFoundException;
import com.example.backend.model.Preferences;
import com.example.backend.repository.PreferencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PreferencesService {

    private final PreferencesRepository preferencesRepository;
    private final ExerciseScheduleService exerciseScheduleService;

    public PreferencesService(PreferencesRepository preferencesRepository, ExerciseScheduleService exerciseScheduleService) {
        this.preferencesRepository = preferencesRepository;
        this.exerciseScheduleService = exerciseScheduleService;
    }

    public Preferences getPreferencesByUserId(Long userId) {
        // Fetch the preferences for the given user ID
        return preferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Preferences not found for user ID: " + userId));
    }

    public Preferences updatePreference(Preferences preferences) {
        // Fetch the existing preference
        Preferences existingPreference = preferencesRepository.findByUserId(preferences.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Preferences not found for user ID: " + preferences.getUserId()));

        // Update the fields of the existing preference
        existingPreference.setFrequency(preferences.getFrequency());
        existingPreference.setGoalCategoryId(preferences.getGoalCategoryId());
        existingPreference.setTimePerDay(preferences.getTimePerDay());

        // Save the updated preference
        Preferences updatedPreference = preferencesRepository.save(existingPreference);

        // Delete and recreate exercise schedules
        exerciseScheduleService.deleteSchedulesByUserId(preferences.getUserId());
        exerciseScheduleService.createDailyExerciseSchedules(updatedPreference);

        return updatedPreference;
    }
}