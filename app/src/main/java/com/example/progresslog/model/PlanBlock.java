package com.example.progresslog.model;

import com.google.firebase.firestore.Exclude;
import java.util.ArrayList;
import java.util.List;

public class PlanBlock {
    private String id;           // ID документа Firestore (не сохраняется внутрь)
    private String userId;       // UID владельца
    private String dayOfWeek;
    private String muscleGroups;
    private List<String> exercises;

    public PlanBlock() {
        // Обязательный пустой конструктор для Firestore
    }

    public PlanBlock(String dayOfWeek, String muscleGroups, List<String> exercises) {
        this.dayOfWeek = dayOfWeek;
        this.muscleGroups = muscleGroups;
        this.exercises = exercises != null ? exercises : new ArrayList<>();
    }

    // @Exclude — чтобы поле id не записывалось как обычное поле документа
    @Exclude public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getMuscleGroups() { return muscleGroups; }
    public void setMuscleGroups(String muscleGroups) { this.muscleGroups = muscleGroups; }

    public List<String> getExercises() { return exercises != null ? exercises : new ArrayList<>(); }
    public void setExercises(List<String> exercises) { this.exercises = exercises; }
}