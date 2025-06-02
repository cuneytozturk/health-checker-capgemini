package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.service.CategoryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category(1L, "Strength", "Exercises to build strength.");
        category2 = new Category(2L, "Cardio", "Exercises to improve cardiovascular health.");
    }

    @Test
    void getAllCategoriesReturnsOk() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(List.of(category1, category2));

        // Act and Assert
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Strength\",\"description\":\"Exercises to build strength.\"}," +
                        "{\"id\":2,\"name\":\"Cardio\",\"description\":\"Exercises to improve cardiovascular health.\"}]"));
    }
}