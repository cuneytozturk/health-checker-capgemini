package com.example.backend.controller;

import com.example.backend.model.Exercise;
import com.example.backend.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseService exerciseService;

    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    void setUp() {
        exercise1 = new Exercise(1L, "Push Up", "A basic push up exercise.", "imageUrl", "videoUrl", 1L, 10);
        exercise2 = new Exercise(2L, "Squat", "A basic squat exercise.", "imageUrl", "videoUrl", 2L, 15);
    }

    @Test
    void getExercisesReturnsOk() throws Exception {
        // Arrange
        when(exerciseService.findAll()).thenReturn(List.of(exercise1, exercise2));

        // Act and Assert
        mockMvc.perform(get("/api/exercises/getall"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Push Up\",\"description\":\"A basic push up exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\",\"categoryId\":1,\"timeRequired\":10}," +
                        "{\"id\":2,\"name\":\"Squat\",\"description\":\"A basic squat exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\",\"categoryId\":2,\"timeRequired\":15}]"));
    }

    @Test
    void addExerciseReturnsOk() throws Exception {
        // Arrange
        String exerciseJson = "{\"name\":\"Push Up\",\"description\":\"A basic push up exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\",\"category\":1,\"timeRequired\":10}";
        doNothing().when(exerciseService).save(any(Exercise.class));

        // Act and Assert
        mockMvc.perform(post("/api/exercises/add")
                        .contentType("application/json")
                        .content(exerciseJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise added successfully!"));
    }

    @Test
    void sendNotificationReturnsOk() throws Exception {
        // Arrange
        Long exerciseId = 1L;
        doNothing().when(exerciseService).sendNotification(exerciseId);

        // Act and Assert
        mockMvc.perform(get("/api/exercises/sendnotification/{id}", exerciseId))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification sent successfully!"));
    }

    @Test
    void getExerciseByIdReturnsOk() throws Exception {
        // Arrange
        Long exerciseId = 1L;
        when(exerciseService.findById(exerciseId)).thenReturn(exercise1);

        // Act and Assert
        mockMvc.perform(get("/api/exercises/get/{id}", exerciseId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Push Up\",\"description\":\"A basic push up exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\",\"categoryId\":1,\"timeRequired\":10}"));
    }
}