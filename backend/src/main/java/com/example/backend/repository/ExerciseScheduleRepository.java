package com.example.backend.repository;

import com.example.backend.model.ExerciseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseScheduleRepository extends JpaRepository<ExerciseSchedule, Long> {
}
