package com.example.backend.repository;

import com.example.backend.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category(null, "Strength", "Exercises to build strength.");
        category2 = new Category(null, "Cardio", "Exercises to improve cardiovascular health.");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    @Test
    void saveCategoryPersistsData() {
        // Arrange
        Category newCategory = new Category(null, "Flexibility", "Exercises to improve flexibility.");

        // Act
        Category savedCategory = categoryRepository.save(newCategory);

        // Assert
        assertNotNull(savedCategory.getId());
        assertEquals("Flexibility", savedCategory.getName());
    }

    @Test
    void findByIdReturnsCategory() {
        // Act
        Optional<Category> foundCategory = categoryRepository.findById(category1.getId());

        // Assert
        assertTrue(foundCategory.isPresent());
        assertEquals("Strength", foundCategory.get().getName());
    }

    @Test
    void findAllReturnsAllCategories() {
        // Act
        List<Category> categories = categoryRepository.findAll();

        // Assert
        assertEquals(2, categories.size());
    }

    @Test
    void deleteByIdRemovesCategory() {
        // Act
        categoryRepository.deleteById(category1.getId());
        Optional<Category> deletedCategory = categoryRepository.findById(category1.getId());

        // Assert
        assertFalse(deletedCategory.isPresent());
    }
}