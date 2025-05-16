package com.example.scheduler.repository;

import com.example.scheduler.model.ExerciseSchedule;
import com.example.scheduler.model.ExerciseSchemeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExerciseScheduleRepository extends JpaRepository<ExerciseSchedule, Long> {
    List<ExerciseSchedule> findByUserId(Long userId);
    List<ExerciseSchedule> findByCreatedAtAfter(LocalDateTime createdAt);
}
