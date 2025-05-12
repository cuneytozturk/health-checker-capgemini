package com.example.backend.service;

import com.example.backend.model.Exercise;
import com.example.backend.model.ExerciseDTO;
import com.example.backend.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

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

//    @Test
//    void sendNotificationSendsPostRequest() {
//        Exercise exercise = new Exercise(1L, "Push Up", "A basic push up exercise.", "videoUrl");
//        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
//
//        exerciseService.sendNotification(1L);
//
//        ExerciseDTO expectedDTO = new ExerciseDTO("Push Up", "A basic push up exercise.", "videoUrl");
//        verify(restTemplate, times(1)).postForObject(eq("http://host.docker.internal:3978/api/notify"), eq(expectedDTO), eq(String.class));
//    }
//
//    @Test
//    void sendNotificationThrowsExceptionWhenExerciseNotFound() {
//        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> exerciseService.sendNotification(1L));
//
//        assertEquals("Exercise with id 1 not found", exception.getMessage());
//        verify(restTemplate, never()).postForObject(anyString(), any(), any());
//    }
}