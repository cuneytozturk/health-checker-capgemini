package com.example.backend.repository;

import com.example.backend.model.Preferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PreferencesRepositoryTest {

    @Autowired
    private PreferencesRepository preferencesRepository;

    private Preferences preferences1;
    private Preferences preferences2;

    @BeforeEach
    void setUp() {
        preferences1 = new Preferences(null, 1L, 2L, 30, 3);
        preferences2 = new Preferences(null, 2L, 3L, 45, 5);
        preferencesRepository.save(preferences1);
        preferencesRepository.save(preferences2);
    }

    @Test
    void savePreferencesPersistsData() {
        // Arrange
        Preferences newPreferences = new Preferences(null, 3L, 4L, 60, 7);

        // Act
        Preferences savedPreferences = preferencesRepository.save(newPreferences);

        // Assert
        assertNotNull(savedPreferences.getId());
        assertEquals(3L, savedPreferences.getUserId());
        assertEquals(4L, savedPreferences.getGoalCategoryId());
    }

    @Test
    void findByIdReturnsPreferences() {
        // Act
        Optional<Preferences> foundPreferences = preferencesRepository.findById(preferences1.getId());

        // Assert
        assertTrue(foundPreferences.isPresent());
        assertEquals(1L, foundPreferences.get().getUserId());
    }

    @Test
    void findByUserIdReturnsPreferences() {
        // Act
        Optional<Preferences> foundPreferences = preferencesRepository.findByUserId(1L);

        // Assert
        assertTrue(foundPreferences.isPresent());
        assertEquals(2L, foundPreferences.get().getGoalCategoryId());
    }

    @Test
    void deleteByIdRemovesPreferences() {
        // Act
        preferencesRepository.deleteById(preferences1.getId());
        Optional<Preferences> deletedPreferences = preferencesRepository.findById(preferences1.getId());

        // Assert
        assertFalse(deletedPreferences.isPresent());
    }
}