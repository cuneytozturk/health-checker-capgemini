package com.example.backend.service;

import com.example.backend.config.exception.InvalidScheduleException;
import com.example.backend.model.ExerciseSchedule;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExerciseScheduleServiceTest {

    private ExerciseScheduleRepository exerciseScheduleRepository;
    private ExerciseScheduleService exerciseScheduleService;

    private List<ExerciseSchedule> exerciseSchedules;

    @BeforeEach
    void setUp() {
        exerciseScheduleRepository = mock(ExerciseScheduleRepository.class);
        exerciseScheduleService = new ExerciseScheduleService(exerciseScheduleRepository);

        exerciseSchedules = List.of(
                new ExerciseSchedule(1L, 101L, 201L, LocalDateTime.of(2023, 10, 1, 8, 0)),
                new ExerciseSchedule(2L, 102L, 202L, LocalDateTime.of(2023, 11, 1, 18, 0))
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
        ExerciseSchedule newSchedule = new ExerciseSchedule(null, 103L, 203L, LocalDateTime.of(2023, 12, 1, 7, 0));
        when(exerciseScheduleRepository.save(any(ExerciseSchedule.class))).thenReturn(newSchedule);

        // Act
        exerciseScheduleService.addExerciseSchedule(newSchedule);

        // Assert
        ArgumentCaptor<ExerciseSchedule> captor = ArgumentCaptor.forClass(ExerciseSchedule.class);
        verify(exerciseScheduleRepository, times(1)).save(captor.capture());
        ExerciseSchedule capturedSchedule = captor.getValue();
        assertEquals(103L, capturedSchedule.getUserId());
        assertEquals(203L, capturedSchedule.getExerciseId());
        assertEquals(LocalDateTime.of(2023, 12, 1, 7, 0), capturedSchedule.getTime());
    }

    //adding schedule with null values throws invalidscheduleexception
    @Test
    void addExerciseScheduleThrowsExceptionWhenNullValues() {
        // Arrange
        ExerciseSchedule newSchedule = new ExerciseSchedule(null, null, null, null);

        // Act & Assert
        assertThrows(InvalidScheduleException.class, () -> exerciseScheduleService.addExerciseSchedule(newSchedule));
        verify(exerciseScheduleRepository, never()).save(any(ExerciseSchedule.class));
    }
}
