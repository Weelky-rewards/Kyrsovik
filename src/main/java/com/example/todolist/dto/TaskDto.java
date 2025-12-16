package com.example.todolist.dto;

import com.example.todolist.model.Priority;
import com.example.todolist.model.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

public class TaskDto {

    private Long id;

    @NotBlank(message = "Заголовок задачи обязателен")
    @Size(max = 100, message = "Заголовок не должен превышать 100 символов")
    private String title;

    @Size(max = 5000, message = "Описание не должно превышать 500 символов")
    private String description;

    private Priority priority = Priority.MEDIUM;

    private TaskStatus status = TaskStatus.PENDING;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}