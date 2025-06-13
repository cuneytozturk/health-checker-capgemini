package com.example.backend.controller;

import com.example.backend.model.ExerciseSchedule;
import com.example.backend.service.ExerciseScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExerciseScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseScheduleService exerciseScheduleService;

    private List<ExerciseSchedule> exerciseSchedules;

    @BeforeEach
    void setUp() {
        exerciseSchedules = List.of(
                new ExerciseSchedule(1L, 101L, 201L, LocalTime.of(8, 0), LocalDateTime.now()),
                new ExerciseSchedule(2L, 102L, 202L, LocalTime.of(18, 0), LocalDateTime.now())
        );
    }

    @Test
    void getAllExerciseSchedulesReturnsOk() throws Exception {
        // Arrange
        when(exerciseScheduleService.getAllExerciseSchedules()).thenReturn(exerciseSchedules);

        // Act and Assert
        mockMvc.perform(get("/api/exerciseschedule/getall"))
                .andExpect(status().isOk());
    }

    @Test
    void getExerciseScheduleByIdReturnsOk() throws Exception {
        // Arrange
        when(exerciseScheduleService.getExerciseScheduleById(1L)).thenReturn(Optional.of(exerciseSchedules.get(0)));

        // Act and Assert
        mockMvc.perform(get("/api/exerciseschedule/get/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getExerciseSchedulesByUserIdReturnsOk() throws Exception {
        // Arrange
        when(exerciseScheduleService.getExerciseSchedulesByUserId(101L)).thenReturn(List.of(exerciseSchedules.get(0)));

        // Act and Assert
        mockMvc.perform(get("/api/exerciseschedule/getbyuserid/101"))
                .andExpect(status().isOk());
    }

    @Test
    void addExerciseScheduleReturnsOk() throws Exception {
        // Arrange
        String scheduleJson = "{\"userId\":101,\"exerciseId\":201,\"time\":\"08:00:00\"}";
        doNothing().when(exerciseScheduleService).addExerciseSchedule(any(ExerciseSchedule.class));

        // Act and Assert
        mockMvc.perform(post("/api/exerciseschedule/add")
                        .contentType("application/json")
                        .content(scheduleJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise schedule added successfully!"));

        // Verify save was called
        ArgumentCaptor<ExerciseSchedule> captor = ArgumentCaptor.forClass(ExerciseSchedule.class);
        verify(exerciseScheduleService, times(1)).addExerciseSchedule(captor.capture());
        ExerciseSchedule capturedSchedule = captor.getValue();
        assertEquals(101L, capturedSchedule.getUserId());
        assertEquals(201L, capturedSchedule.getExerciseId());
        assertEquals(LocalTime.of(8, 0), capturedSchedule.getTime());
    }
}