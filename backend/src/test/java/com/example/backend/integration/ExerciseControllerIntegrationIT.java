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
public class ExerciseControllerIntegrationIT extends BaseIntegrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        exerciseRepository.deleteAll();
    }

    @Test
    void getAllExercisesReturnsEmptyListInitially() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/exercises/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void addExerciseAndRetrieveIt() throws Exception {
        // Arrange
        String exerciseJson = """
                {
                    "name": "Push Up",
                    "description": "A basic push-up exercise",
                    "imageUrl": "imageUrl",
                    "videoUrl": "videoUrl"
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
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Push Up"))
                .andExpect(jsonPath("$[0].description").value("A basic push-up exercise"));
    }

    @Test
    void getExerciseByIdReturnsCorrectExercise() throws Exception {
        // Arrange
        Exercise exercise = new Exercise(null, "Push Up", "A basic push-up exercise", "imageUrl", "videoUrl");
        Exercise savedExercise = exerciseRepository.save(exercise);

        // Act & Assert
        mockMvc.perform(get("/api/exercises/get/" + savedExercise.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push Up"))
                .andExpect(jsonPath("$.description").value("A basic push-up exercise"));
    }

    @Test
    void sendNotificationReturnsSuccessMessage() throws Exception {
        // Arrange
        Exercise exercise = new Exercise(null, "Push Up", "A basic push-up exercise", "imageUrl", "videoUrl");
        Exercise savedExercise = exerciseRepository.save(exercise);

        // Act & Assert
        mockMvc.perform(get("/api/exercises/sendnotification/" + savedExercise.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully!"));
    }
}