package com.example.backend.integration;

import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExercisesIT extends BaseIntegrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    void setUp() {
        // Clear the database and initialize test data
        exerciseRepository.deleteAll();

        exercise1 = new Exercise(null, "Push Up", "A basic push-up exercise", "imageUrl1", "videoUrl1", 1L, 10);
        exercise2 = new Exercise(null, "Squat", "A basic squat exercise", "imageUrl2", "videoUrl2", 1L, 15);

        exerciseRepository.save(exercise1);
        exerciseRepository.save(exercise2);
    }

    @Test
    void getAllExercisesReturnsAllExercises() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/exercises/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Push Up"))
                .andExpect(jsonPath("$[1].name").value("Squat"));
    }

    @Test
    void addExerciseAndRetrieveIt() throws Exception {
        // Arrange
        String exerciseJson = """
                {
                    "name": "Plank",
                    "description": "A core strength exercise",
                    "imageUrl": "imageUrl3",
                    "videoUrl": "videoUrl3",
                    "categoryId": 2,
                    "timeRequired": 5
                }
                """;

        // Act
        mockMvc.perform(post("/api/exercises/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exerciseJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise added successfully!"));

        // Assert
        mockMvc.perform(get("/api/exercises/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name").value("Plank"))
                .andExpect(jsonPath("$[2].description").value("A core strength exercise"));
    }

    @Test
    void getExerciseByIdReturnsCorrectExercise() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/exercises/get/" + exercise1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push Up"))
                .andExpect(jsonPath("$.description").value("A basic push-up exercise"));
    }

    @Test
    void sendNotificationReturnsSuccessMessage() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/exercises/sendnotification/" + exercise1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully!"));
    }
}