package com.example.backend.service;

import com.example.backend.model.Preferences;
import com.example.backend.repository.PreferencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferencesService {

    private final PreferencesRepository preferencesRepository;

    public PreferencesService(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public Preferences addPreference(Preferences preferences) {
        return preferencesRepository.save(preferences);
    }

    public List<Preferences> getAllPreferences() {
        return preferencesRepository.findAll();
    }
}