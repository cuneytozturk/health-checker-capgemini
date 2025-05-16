package com.example.backend.controller;

import com.example.backend.model.ExerciseSchedule;
import com.example.backend.repository.ExerciseRepository;
import com.example.backend.repository.ExerciseScheduleRepository;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exerciseschedule", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseScheduleController {


    private final ExerciseScheduleRepository exerciseScheduleRepository;

    public ExerciseScheduleController(ExerciseScheduleRepository exerciseScheduleRepository) {
        this.exerciseScheduleRepository = exerciseScheduleRepository;
    }

    @GetMapping("/getall")
     public List<ExerciseSchedule> getAllExerciseSchedules() {
         return exerciseScheduleRepository.findAll();
     }

     @GetMapping("/get/{id}")
    public ExerciseSchedule getExerciseScheduleById(@PathVariable Long id) {
        return exerciseScheduleRepository.findById(id).orElse(null);
    }

    @GetMapping("/getbyuserid/{userId}")
    public List<ExerciseSchedule> getExerciseSchedulesByUserId(@PathVariable Long userId) {
        ExerciseSchedule example = new ExerciseSchedule();
        example.setUserId(userId);
        return exerciseScheduleRepository.findAll(Example.of(example));
    }

     @PostMapping("/add")
        public String addExerciseSchedule(@RequestBody ExerciseSchedule exerciseSchedule) {
            exerciseScheduleRepository.save(exerciseSchedule);
            return "Exercise schedule added successfully!";
        }

}
