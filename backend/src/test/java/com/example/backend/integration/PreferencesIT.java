package com.example.backend.integration;

import com.example.backend.model.Preferences;
import com.example.backend.repository.PreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PreferencesIT extends BaseIntegrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PreferencesRepository preferencesRepository;

    private Preferences preferences1;

    @BeforeEach
    void setUp() {
        // Clear the database and initialize test data
        preferencesRepository.deleteAll();

        preferences1 = new Preferences(null, 1L, 2L, 30, 3);
        Preferences preferences2 = new Preferences(null, 2L, 3L, 45, 5);

        preferencesRepository.save(preferences1);
        preferencesRepository.save(preferences2);
    }

    @Test
    void getPreferencesByUserIdReturnsPreferences() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/preferences/{userId}", preferences1.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(preferences1.getUserId()))
                .andExpect(jsonPath("$.goalCategoryId").value(preferences1.getGoalCategoryId()))
                .andExpect(jsonPath("$.timePerDay").value(preferences1.getTimePerDay()))
                .andExpect(jsonPath("$.frequency").value(preferences1.getFrequency()));
    }

    @Test
    void updatePreferenceUpdatesAndReturnsPreferences() throws Exception {
        // Arrange
        String updatedPreferencesJson = """
                {
                    "id": %d,
                    "userId": %d,
                    "goalCategoryId": 4,
                    "timePerDay": 60,
                    "frequency": 7
                }
                """.formatted(preferences1.getId(), preferences1.getUserId());

        // Act & Assert
        mockMvc.perform(put("/api/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPreferencesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goalCategoryId").value(4))
                .andExpect(jsonPath("$.timePerDay").value(60))
                .andExpect(jsonPath("$.frequency").value(7));
    }

    @Test
    void getPreferencesByUserIdReturnsErrorMessageWhenPreferencesDoNotExist() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/preferences/{userId}", 999L))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred: Preferences not found for user ID: 999"));
    }
}