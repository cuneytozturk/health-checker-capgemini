package com.example.backend.repository;

import com.example.backend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByCategoryId(Long goalCategoryId);
}
