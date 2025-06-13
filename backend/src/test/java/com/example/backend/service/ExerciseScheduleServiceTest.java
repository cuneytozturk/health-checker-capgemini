package com.example.backend.service;

import com.example.backend.config.exception.InvalidScheduleException;
import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseSchedule;
import com.example.backend.model.Preferences;
import com.example.backend.repository.ExerciseRepository;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExerciseScheduleServiceTest {

    private ExerciseScheduleRepository exerciseScheduleRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseScheduleService exerciseScheduleService;

    private List<ExerciseSchedule> exerciseSchedules;

    @BeforeEach
    void setUp() {
        exerciseScheduleRepository = mock(ExerciseScheduleRepository.class);
        exerciseRepository = mock(ExerciseRepository.class);
        exerciseScheduleService = new ExerciseScheduleService(exerciseScheduleRepository, exerciseRepository);

        exerciseSchedules = List.of(
                new ExerciseSchedule(1L, 101L, 201L, LocalTime.of(8, 0), LocalDateTime.now()),
                new ExerciseSchedule(2L, 102L, 202L, LocalTime.of(18, 0), LocalDateTime.now())
        );
    }

    @Test
    void getAllExerciseSchedulesReturnsAllSchedules() {
        // Arrange
        when(exerciseScheduleRepository.findAll()).thenReturn(exerciseSchedules);

        // Act
        List<ExerciseSchedule> result = exerciseScheduleService.getAllExerciseSchedules();

        // Assert
        assertEquals(2, result.size());
        assertEquals(exerciseSchedules, result);
        verify(exerciseScheduleRepository, times(1)).findAll();
    }

    @Test
    void getExerciseScheduleByIdReturnsSchedule() {
        // Arrange
        when(exerciseScheduleRepository.findById(1L)).thenReturn(Optional.of(exerciseSchedules.get(0)));

        // Act
        Optional<ExerciseSchedule> result = exerciseScheduleService.getExerciseScheduleById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(exerciseSchedules.get(0), result.get());
        verify(exerciseScheduleRepository, times(1)).findById(1L);
    }

    @Test
    void getExerciseScheduleByIdReturnsEmptyWhenNotFound() {
        // Arrange
        when(exerciseScheduleRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<ExerciseSchedule> result = exerciseScheduleService.getExerciseScheduleById(3L);

        // Assert
        assertFalse(result.isPresent());
        verify(exerciseScheduleRepository, times(1)).findById(3L);
    }

    @Test
    void getExerciseSchedulesByUserIdReturnsSchedules() {
        // Arrange
        when(exerciseScheduleRepository.findAll(any(Example.class))).thenReturn(List.of(exerciseSchedules.get(0)));

        // Act
        List<ExerciseSchedule> result = exerciseScheduleService.getExerciseSchedulesByUserId(101L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(exerciseSchedules.get(0), result.get(0));
        verify(exerciseScheduleRepository, times(1)).findAll(any(Example.class));
    }

    @Test
    void addExerciseScheduleSavesSchedule() {
        // Arrange
        ExerciseSchedule newSchedule = new ExerciseSchedule(null, 103L, 203L, LocalTime.of(7, 0), LocalDateTime.now());
        when(exerciseScheduleRepository.save(any(ExerciseSchedule.class))).thenReturn(newSchedule);

        // Act
        exerciseScheduleService.addExerciseSchedule(newSchedule);

        // Assert
        ArgumentCaptor<ExerciseSchedule> captor = ArgumentCaptor.forClass(ExerciseSchedule.class);
        verify(exerciseScheduleRepository, times(1)).save(captor.capture());
        ExerciseSchedule capturedSchedule = captor.getValue();
        assertEquals(103L, capturedSchedule.getUserId());
        assertEquals(203L, capturedSchedule.getExerciseId());
        assertEquals(LocalTime.of(7, 0), capturedSchedule.getTime());
        assertNotNull(capturedSchedule.getCreatedDate());
    }

    @Test
    void addExerciseScheduleThrowsExceptionWhenNullValues() {
        // Arrange
        ExerciseSchedule newSchedule = new ExerciseSchedule(null, null, null, null, null);

        // Act & Assert
        assertThrows(InvalidScheduleException.class, () -> exerciseScheduleService.addExerciseSchedule(newSchedule));
        verify(exerciseScheduleRepository, never()).save(any(ExerciseSchedule.class));
    }

    @Test
    void deleteSchedulesByUserIdDeletesSchedules() {
        // Arrange
        when(exerciseScheduleRepository.findAll(any(Example.class))).thenReturn(exerciseSchedules);

        // Act
        exerciseScheduleService.deleteSchedulesByUserId(101L);

        // Assert
        verify(exerciseScheduleRepository, times(1)).deleteAll(exerciseSchedules);
    }

    @Test
    void deleteSchedulesByUserIdDoesNothingWhenNoSchedulesFound() {
        // Arrange
        when(exerciseScheduleRepository.findAll(any(Example.class))).thenReturn(List.of());

        // Act
        exerciseScheduleService.deleteSchedulesByUserId(101L);

        // Assert
        verify(exerciseScheduleRepository, never()).deleteAll(any());
    }

    @Test
    void createDailyExerciseSchedulesCreatesSchedules() {
        // Arrange
        Preferences preferences = new Preferences();
        preferences.setUserId(101L);
        preferences.setGoalCategoryId(1L);
        preferences.setTimePerDay(60); // 60 minutes
        preferences.setFrequency(2);  // 2 sessions per day

        List<Exercise> exercises = List.of(
                new Exercise(201L, "Push Up", "A basic push up exercise.", "imageUrl", "videoUrl", 1L, 10),
                new Exercise(202L, "Squat", "A basic squat exercise.", "imageUrl", "videoUrl", 1L, 15)
        );

        when(exerciseRepository.findByCategoryId(1L)).thenReturn(exercises);

        // Act
        exerciseScheduleService.createDailyExerciseSchedules(preferences);

        // Assert
        ArgumentCaptor<List<ExerciseSchedule>> captor = ArgumentCaptor.forClass(List.class);
        verify(exerciseScheduleRepository, times(1)).saveAll(captor.capture());
        List<ExerciseSchedule> capturedSchedules = captor.getValue();

        assertEquals(4, capturedSchedules.size()); // 4 planned exercises
        assertEquals(101L, capturedSchedules.get(0).getUserId());
        assertEquals(201L, capturedSchedules.get(0).getExerciseId());
        assertEquals(LocalTime.of(8, 0), capturedSchedules.get(0).getTime());
    }
}