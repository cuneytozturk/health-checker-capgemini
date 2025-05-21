package com.example.backend.repository;

import com.example.backend.model.ExerciseSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExerciseScheduleRepositoryTest {

    @Autowired
    private ExerciseScheduleRepository exerciseScheduleRepository;

    private ExerciseSchedule schedule1;
    private ExerciseSchedule schedule2;

    @BeforeEach
    void setUp() {
        schedule1 = new ExerciseSchedule(null, 101L, 201L, LocalDateTime.of(2023, 10, 1, 8, 0));
        schedule2 = new ExerciseSchedule(null, 102L, 202L, LocalDateTime.of(2023, 11, 1, 18, 0));
        exerciseScheduleRepository.save(schedule1);
        exerciseScheduleRepository.save(schedule2);
    }

    @Test
    void saveExerciseSchedulePersistsData() {
        // Arrange
        ExerciseSchedule newSchedule = new ExerciseSchedule(null, 103L, 203L, LocalDateTime.of(2023, 12, 1, 7, 0));

        // Act
        ExerciseSchedule savedSchedule = exerciseScheduleRepository.save(newSchedule);

        // Assert
        assertNotNull(savedSchedule.getId());
        assertEquals(103L, savedSchedule.getUserId());
        assertEquals(203L, savedSchedule.getExerciseId());
        assertEquals(LocalDateTime.of(2023, 12, 1, 7, 0), savedSchedule.getTime());
    }

    @Test
    void findByIdReturnsExerciseSchedule() {
        // Act
        Optional<ExerciseSchedule> foundSchedule = exerciseScheduleRepository.findById(schedule1.getId());

        // Assert
        assertTrue(foundSchedule.isPresent());
        assertEquals(schedule1.getUserId(), foundSchedule.get().getUserId());
        assertEquals(schedule1.getExerciseId(), foundSchedule.get().getExerciseId());
        assertEquals(schedule1.getTime(), foundSchedule.get().getTime());
    }

    @Test
    void findAllReturnsAllExerciseSchedules() {
        // Act
        List<ExerciseSchedule> schedules = exerciseScheduleRepository.findAll();

        // Assert
        assertEquals(2, schedules.size());
    }

    @Test
    void deleteByIdRemovesExerciseSchedule() {
        // Act
        exerciseScheduleRepository.deleteById(schedule1.getId());
        Optional<ExerciseSchedule> deletedSchedule = exerciseScheduleRepository.findById(schedule1.getId());

        // Assert
        assertFalse(deletedSchedule.isPresent());
    }
}