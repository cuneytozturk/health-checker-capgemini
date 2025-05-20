package com.example.backend.config.exception;

public class InvalidExerciseException extends RuntimeException {
    public InvalidExerciseException(String message) {
        super(message);
    }
}