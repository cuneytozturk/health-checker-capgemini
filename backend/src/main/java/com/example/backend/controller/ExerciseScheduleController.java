package com.example.backend.controller;

import com.example.backend.model.ExerciseSchedule;
import com.example.backend.service.ExerciseScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exerciseschedule", produces = "application/json")
@CrossOrigin(origins = "${api.frontend.url}")
public class ExerciseScheduleController {

    private final ExerciseScheduleService exerciseScheduleService;

    public ExerciseScheduleController(ExerciseScheduleService exerciseScheduleService) {
        this.exerciseScheduleService = exerciseScheduleService;
    }

    @GetMapping("/getall")
    public List<ExerciseSchedule> getAllExerciseSchedules() {
        return exerciseScheduleService.getAllExerciseSchedules();
    }

    @GetMapping("/get/{id}")
    public ExerciseSchedule getExerciseScheduleById(@PathVariable Long id) {
        return exerciseScheduleService.getExerciseScheduleById(id).orElse(null);
    }

    @GetMapping("/getbyuserid/{userId}")
    public List<ExerciseSchedule> getExerciseSchedulesByUserId(@PathVariable Long userId) {
        return exerciseScheduleService.getExerciseSchedulesByUserId(userId);
    }

    @PostMapping("/add")
    public String addExerciseSchedule(@RequestBody ExerciseSchedule exerciseSchedule) {
        exerciseScheduleService.addExerciseSchedule(exerciseSchedule);
        return "Exercise schedule added successfully!";
    }
}