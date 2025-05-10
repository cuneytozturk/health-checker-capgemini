package com.example.backend.model;

import jakarta.persistence.*;

@Entity
public class Exercise {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;
        private String videoUrl;

    public Exercise() {}

    public Exercise(Long id, String name, String description, String videoUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    @Override
        public String toString() {
            return "Exercise{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

