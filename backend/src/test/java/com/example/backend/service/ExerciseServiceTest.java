package com.example.backend.service;

import com.example.backend.config.exception.EntityNotFoundException;
import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @InjectMocks
    private ExerciseService exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private BotServiceClient botServiceClient;

    @Test
    void findAllReturnsAllExercises() {
        //arrange
        List<Exercise> mockExercises = List.of(
                new Exercise(1L, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl"),
                new Exercise(2L, "Squat", "A basic squat exercise.","imageUrl", "videoUrl")
        );
        when(exerciseRepository.findAll()).thenReturn(mockExercises);

        //act
        List<Exercise> exercises = exerciseService.findAll();

        //assert
        assertEquals(2, exercises.size());
        assertEquals("Push Up", exercises.get(0).getName());
        verify(exerciseRepository, times(1)).findAll();
    }

    @Test
    void saveCallsRepositorySave() {
        //arrange
        Exercise exercise = new Exercise(1L, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl");

        //act
        exerciseService.save(exercise);

        //assert
        ArgumentCaptor<Exercise> captor = ArgumentCaptor.forClass(Exercise.class);
        verify(exerciseRepository, times(1)).save(captor.capture());
        assertEquals("Push Up", captor.getValue().getName());
    }

    @Test
    void findByIdReturnsExerciseWhenExists() {
        // Arrange
        Exercise mockExercise = new Exercise(1L, "Push Up", "A basic push up exercise.", "imageUrl", "videoUrl");
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(mockExercise));

        // Act
        Exercise result = exerciseService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Push Up", result.getName());
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenNotFound() {
        // Arrange
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> exerciseService.findById(1L));
        assertEquals("Exercise with id 1 not found", exception.getMessage());
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    void sendNotificationCallsBotServiceClient() {
        // Arrange
        Exercise mockExercise = new Exercise(1L, "Push Up", "A basic push up exercise.", "imageUrl", "videoUrl");
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(mockExercise));

        // Act
        exerciseService.sendNotification(1L);

        // Assert
        verify(botServiceClient, times(1)).sendNotification(mockExercise);
    }

    @Test
    void sendNotificationThrowsExceptionWhenExerciseNotFound() {
        // Arrange
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> exerciseService.sendNotification(1L));
        assertEquals("Exercise with id 1 not found", exception.getMessage());
        verify(botServiceClient, never()).sendNotification(any());
    }
}