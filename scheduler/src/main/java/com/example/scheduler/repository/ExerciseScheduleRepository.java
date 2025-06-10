package com.example.scheduler.repository;

import com.example.scheduler.model.ExerciseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseScheduleRepository extends JpaRepository<ExerciseSchedule, Long> {
    List<ExerciseSchedule> findByUserId(Long userId);
    List<ExerciseSchedule> findByCreatedDateAfter(LocalDateTime createdDate);

}
