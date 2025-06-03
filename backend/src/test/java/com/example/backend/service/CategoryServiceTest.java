package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void getAllCategoriesReturnsAllCategories() {
        // Arrange
        List<Category> mockCategories = List.of(
                new Category(1L, "Strength", "Exercises to build strength."),
                new Category(2L, "Cardio", "Exercises to improve cardiovascular health.")
        );
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        // Act
        List<Category> categories = categoryService.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        assertEquals("Strength", categories.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }
}