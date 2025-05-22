package com.example.backend.repository;

import com.example.backend.model.Exercise;
import org.junit.jupiter.api.BeforeEach;
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

    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    void setUp() {
        exercise1 = new Exercise(null, "Push Up", "A basic push up exercise.", "imageUrl", "videoUrl", 1L, 10);
        exercise2 = new Exercise(null, "Squat", "A basic squat exercise.", "imageUrl", "videoUrl", 1L, 15);
        exerciseRepository.save(exercise1);
        exerciseRepository.save(exercise2);
    }

    @Test
    void saveExercisePersistsData() {
        // Arrange
        Exercise newExercise = new Exercise(null, "Plank", "A core strength exercise.", "imageUrl", "videoUrl", 2L, 5);

        // Act
        Exercise savedExercise = exerciseRepository.save(newExercise);

        // Assert
        assertNotNull(savedExercise.getId());
        assertEquals("Plank", savedExercise.getName());
    }

    @Test
    void findByIdReturnsExercise() {
        // Act
        Optional<Exercise> foundExercise = exerciseRepository.findById(exercise1.getId());

        // Assert
        assertTrue(foundExercise.isPresent());
        assertEquals("Push Up", foundExercise.get().getName());
    }

    @Test
    void findAllReturnsAllExercises() {
        // Act
        List<Exercise> exercises = exerciseRepository.findAll();

        // Assert
        assertEquals(2, exercises.size());
    }

    @Test
    void deleteByIdRemovesExercise() {
        // Act
        exerciseRepository.deleteById(exercise1.getId());
        Optional<Exercise> deletedExercise = exerciseRepository.findById(exercise1.getId());

        // Assert
        assertFalse(deletedExercise.isPresent());
    }
}