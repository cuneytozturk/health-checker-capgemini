package com.example.backend.controller;

import com.example.backend.model.Preferences;
import com.example.backend.service.PreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {

    private final PreferencesService preferencesService;

    public PreferencesController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @PostMapping
    public ResponseEntity<Preferences> addPreference(@RequestBody Preferences preferences) {
        Preferences savedPreference = preferencesService.addPreference(preferences);
        return ResponseEntity.ok(savedPreference);
    }

    @GetMapping
    public ResponseEntity<List<Preferences>> getAllPreferences() {
        List<Preferences> preferences = preferencesService.getAllPreferences();
        return ResponseEntity.ok(preferences);
    }
}