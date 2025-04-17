package com.example.backend.model;

public class ExerciseDTO {
    private String name;
    private String description;
    private String videoUrl;

    public ExerciseDTO(String name, String description, String videoUrl) {
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
