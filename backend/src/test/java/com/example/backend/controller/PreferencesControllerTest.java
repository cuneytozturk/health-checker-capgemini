package com.example.backend.controller;

import com.example.backend.model.Preferences;
import com.example.backend.service.PreferencesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PreferencesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PreferencesService preferencesService;

    private Preferences preferences;

    @BeforeEach
    void setUp() {
        preferences = new Preferences(1L, 1L, 2L, 30, 3);
    }

    @Test
    void getPreferencesByUserIdReturnsOk() throws Exception {
        // Arrange
        when(preferencesService.getPreferencesByUserId(1L)).thenReturn(preferences);

        // Act and Assert
        mockMvc.perform(get("/api/preferences/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"userId\":1,\"goalCategoryId\":2,\"timePerDay\":30,\"frequency\":3}"));
    }

    @Test
    void updatePreferenceReturnsOk() throws Exception {
        // Arrange
        Preferences updatedPreferences = new Preferences(1L, 1L, 2L, 45, 5);
        when(preferencesService.updatePreference(any(Preferences.class))).thenReturn(updatedPreferences);

        String updatedPreferencesJson = "{\"id\":1,\"userId\":1,\"goalCategoryId\":2,\"timePerDay\":45,\"frequency\":5}";

        // Act and Assert
        mockMvc.perform(put("/api/preferences")
                        .contentType("application/json")
                        .content(updatedPreferencesJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedPreferencesJson));
    }
}