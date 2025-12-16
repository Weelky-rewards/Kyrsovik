package com.example.todolist.model;

public enum TaskStatus {
    PENDING("В процессе"),
    COMPLETED("Выполнено");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}