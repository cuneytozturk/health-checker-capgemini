package com.example.backend.controller;

import com.example.backend.model.Preferences;
import com.example.backend.service.PreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "${api.frontend.url}")
public class PreferencesController {

    private final PreferencesService preferencesService;

    public PreferencesController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Preferences> getPreferencesByUserId(@PathVariable Long userId) {
        Preferences preferences = preferencesService.getPreferencesByUserId(userId);
        return ResponseEntity.ok(preferences);
    }

    @PutMapping
    public ResponseEntity<Preferences> updatePreference(@RequestBody Preferences preferences) {
        Preferences updatedPreference = preferencesService.updatePreference(preferences);
        return ResponseEntity.ok(updatedPreference);
    }
}