package com.example.backend.model;

import jakarta.persistence.*;

@Entity
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(name = "goal_category_id", nullable = false)
    private Long goalCategoryId;

    @Column(nullable = false)
    private int timePerDay;

    @Column(nullable = false)
    private int frequency;

    public Preferences() {
    }

    public Preferences(Long id, Long userId, Long goalCategoryId, int timePerDay, int frequency) {
        this.id = id;
        this.userId = userId;
        this.goalCategoryId = goalCategoryId;
        this.timePerDay = timePerDay;
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoalCategoryId() {
        return goalCategoryId;
    }

    public void setGoalCategoryId(Long goalCategoryId) {
        this.goalCategoryId = goalCategoryId;
    }

    public int getTimePerDay() {
        return timePerDay;
    }

    public void setTimePerDay(int timePerDay) {
        this.timePerDay = timePerDay;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "id=" + id +
                ", userId=" + userId +
                ", goalCategoryId=" + goalCategoryId +
                ", timePerDay=" + timePerDay +
                ", frequency=" + frequency +
                '}';
    }
}