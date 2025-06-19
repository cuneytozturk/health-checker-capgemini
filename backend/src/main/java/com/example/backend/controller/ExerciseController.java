package com.example.backend.controller;

import com.example.backend.service.ExerciseService;
import com.example.backend.model.Exercise;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exercises", produces = "application/json")
@CrossOrigin(origins = "${api.frontend.url}")
public class  ExerciseController {
    private final ExerciseService exerciseService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ExerciseController.class);

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/getall")
    public List<Exercise> getAllExercises() {
        logger.info("Fetching all exercises");
        return exerciseService.findAll();
    }

    @PostMapping("/add")
    public String addExercise(@RequestBody Exercise exercise) {
        logger.info("Adding exercise: {}", exercise);
        exerciseService.save(exercise);
        return "Exercise added successfully!";
    }

    @GetMapping("/sendnotification/{id}")
    public String sendNotification(@PathVariable Long id) {
        logger.info("Sending notification for exercise with id: {}", id);
        exerciseService.sendNotification(id);
        return "Notification sent successfully!";
    }

    @GetMapping("/get/{id}")
    public Exercise getExerciseById(@PathVariable Long id) {
        logger.info("Fetching exercise with id: {}", id);
        return exerciseService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id) {
        logger.info("Deleting exercise with id: {}", id);
        exerciseService.deleteById(id);
        return "Exercise deleted successfully!";
    }

}
