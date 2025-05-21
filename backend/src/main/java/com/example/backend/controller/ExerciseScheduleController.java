package com.example.backend.controller;

import com.example.backend.model.ExerciseSchedule;
import com.example.backend.model.Preferences;
import com.example.backend.service.ExerciseScheduleService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exerciseschedule", produces = "application/json")
@CrossOrigin(origins = "${api.frontend.url}")
public class ExerciseScheduleController {

    private final ExerciseScheduleService exerciseScheduleService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ExerciseScheduleController.class);

    public ExerciseScheduleController(ExerciseScheduleService exerciseScheduleService) {
        this.exerciseScheduleService = exerciseScheduleService;
    }

    @GetMapping("/getall")
    public List<ExerciseSchedule> getAllExerciseSchedules() {
        logger.info("Fetching all exercise schedules");
        return exerciseScheduleService.getAllExerciseSchedules();
    }

    @GetMapping("/get/{id}")
    public ExerciseSchedule getExerciseScheduleById(@PathVariable Long id) {
        logger.info("Fetching exercise schedule with id: {}", id);
        return exerciseScheduleService.getExerciseScheduleById(id).orElse(null);
    }

    @GetMapping("/getbyuserid/{userId}")
    public List<ExerciseSchedule> getExerciseSchedulesByUserId(@PathVariable Long userId) {
        logger.info("Fetching exercise schedules for user with id: {}", userId);
        return exerciseScheduleService.getExerciseSchedulesByUserId(userId);
    }

    @PostMapping("/add")
    public String addExerciseSchedule(@RequestBody ExerciseSchedule exerciseSchedule) {
        logger.info("Adding exercise schedule: {}", exerciseSchedule);
        exerciseScheduleService.addExerciseSchedule(exerciseSchedule);
        return "Exercise schedule added successfully!";
    }

    @GetMapping("/createSchedules")
    public String createDailyExerciseSchedules() {
        logger.info("Creating daily exercise schedules");
        exerciseScheduleService.createDailyExerciseSchedules(new Preferences(1L,1L,1L,100,4));
        return "Daily exercise schedules created successfully!";
    }
}