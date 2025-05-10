package com.example.backend.controller;

import com.example.backend.service.ExerciseService;
import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exercises", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseRepository exerciseRepository, ExerciseService exerciseService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/getall")
    public List<Exercise> getAllExercises() {
        return exerciseService.findAll();
    }

    @PostMapping("/add")
    public String addExercise(@RequestBody Exercise exercise) {
        exerciseService.save(exercise);
        return "Exercise added successfully!";
    }

    @GetMapping("/sendnotification/{id}")
    public String sendNotification(@PathVariable Long id) {
        exerciseService.sendNotification(id);
        return "Notification sent successfully!";
    }
}
