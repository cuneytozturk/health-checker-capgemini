package com.example.backend.repository;

import com.example.backend.model.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void saveExercisePersistsData() {
        // Arrange
        Exercise exercise = new Exercise(null, "Push Up", "A basic push up exercise.","imageUrl" ,"videoUrl");

        // Act
        Exercise savedExercise = exerciseRepository.save(exercise);

        // Assert
        assertNotNull(savedExercise.getId());
        assertEquals("Push Up", savedExercise.getName());
    }

    @Test
    void findByIdReturnsExercise() {
        // Arrange
        Exercise exercise = new Exercise(null, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl");
        Exercise savedExercise = exerciseRepository.save(exercise);

        // Act
        Optional<Exercise> foundExercise = exerciseRepository.findById(savedExercise.getId());

        // Assert
        assertTrue(foundExercise.isPresent());
        assertEquals("Push Up", foundExercise.get().getName());
    }

    @Test
    void findAllReturnsAllExercises() {
        // Arrange
        Exercise exercise1 = new Exercise(null, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl");
        Exercise exercise2 = new Exercise(null, "Squat", "A basic squat exercise.","imageUrl", "videoUrl");
        exerciseRepository.save(exercise1);
        exerciseRepository.save(exercise2);

        // Act
        List<Exercise> exercises = exerciseRepository.findAll();

        // Assert
        assertEquals(2, exercises.size());
    }

    @Test
    void deleteByIdRemovesExercise() {
        // Arrange
        Exercise exercise = new Exercise(null, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl");
        Exercise savedExercise = exerciseRepository.save(exercise);

        // Act
        exerciseRepository.deleteById(savedExercise.getId());
        Optional<Exercise> deletedExercise = exerciseRepository.findById(savedExercise.getId());

        // Assert
        assertFalse(deletedExercise.isPresent());
    }
}