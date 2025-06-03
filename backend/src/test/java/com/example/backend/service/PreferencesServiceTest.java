package com.example.backend.service;

import com.example.backend.config.exception.EntityNotFoundException;
import com.example.backend.model.Preferences;
import com.example.backend.repository.PreferencesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreferencesServiceTest {

    @InjectMocks
    private PreferencesService preferencesService;

    @Mock
    private PreferencesRepository preferencesRepository;

    @Mock
    private ExerciseScheduleService exerciseScheduleService;

    @Test
    void getPreferencesByUserIdReturnsPreferences() {
        // Arrange
        Preferences mockPreferences = new Preferences(1L, 1L, 2L, 30, 3);
        when(preferencesRepository.findByUserId(1L)).thenReturn(Optional.of(mockPreferences));

        // Act
        Preferences result = preferencesService.getPreferencesByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(preferencesRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getPreferencesByUserIdThrowsExceptionWhenNotFound() {
        // Arrange
        when(preferencesRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> preferencesService.getPreferencesByUserId(1L));
        assertEquals("Preferences not found for user ID: 1", exception.getMessage());
        verify(preferencesRepository, times(1)).findByUserId(1L);
    }

    @Test
    void updatePreferenceUpdatesAndReturnsPreferences() {
        // Arrange
        Preferences existingPreferences = new Preferences(1L, 1L, 2L, 30, 3);
        Preferences updatedPreferences = new Preferences(1L, 1L, 3L, 45, 5);
        when(preferencesRepository.findByUserId(1L)).thenReturn(Optional.of(existingPreferences));
        when(preferencesRepository.save(any(Preferences.class))).thenReturn(updatedPreferences);

        // Act
        Preferences result = preferencesService.updatePreference(updatedPreferences);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getGoalCategoryId());
        assertEquals(45, result.getTimePerDay());
        verify(preferencesRepository, times(1)).findByUserId(1L);
        verify(preferencesRepository, times(1)).save(existingPreferences);
        verify(exerciseScheduleService, times(1)).deleteSchedulesByUserId(1L);
        verify(exerciseScheduleService, times(1)).createDailyExerciseSchedules(updatedPreferences);
    }
}