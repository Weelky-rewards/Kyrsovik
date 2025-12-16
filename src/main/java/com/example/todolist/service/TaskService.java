package com.example.todolist.service;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task createTask(TaskDto taskDto, User user);
    Task updateTask(Long id, TaskDto taskDto, User user);
    void deleteTask(Long id, User user);
    Task getTaskById(Long id, User user);
    Page<Task> getUserTasks(User user, Pageable pageable);
    Task toggleTaskStatus(Long id, User user);
}