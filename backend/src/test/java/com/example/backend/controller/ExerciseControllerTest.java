package com.example.backend.controller;

import com.example.backend.model.Exercise;
import com.example.backend.repository.ExerciseRepository;
import com.example.backend.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ExerciseController.class)
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseService exerciseService;

    @MockitoBean
    private ExerciseRepository exerciseRepository;

    @Test
    void getExercisesReturnsOk() throws Exception {
        //arrange
        when(exerciseService.findAll()).thenReturn(List.of(
                new Exercise(1L,"Push Up", "A basic push up exercise.","imageUrl", "videoUrl"),
                new Exercise(2L,"Squat", "A basic squat exercise.","imageUrl" ,"videoUrl")
        ));

        //act and assert
        mockMvc.perform(get("/api/exercises/getall"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Push Up\",\"description\":\"A basic push up exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\"}," +
                        "{\"id\":2,\"name\":\"Squat\",\"description\":\"A basic squat exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\"}]"));
    }

    @Test
    void addExerciseReturnsOk() throws Exception {
        //arrange
        String exerciseJson = "{\"name\":\"Push Up\",\"description\":\"A basic push up exercise.\",\"imageUrl\":\"imageUrl\",\"videoUrl\":\"videoUrl\"}";
        Exercise exercise = new Exercise(1L, "Push Up", "A basic push up exercise.","imageUrl", "videoUrl");
        doNothing().when(exerciseService).save(exercise);

        //act and assert
        mockMvc.perform(post("/api/exercises/add")
                .contentType("application/json")
                .content(exerciseJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise added successfully!"));
    }
}