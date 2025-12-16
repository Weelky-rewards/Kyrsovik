package com.example.todolist.service;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskStatus;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(TaskDto taskDto, User user) {
        System.out.println("=== DEBUG: Creating task ===");
        System.out.println("User: " + user.getUsername());
        System.out.println("Title: " + taskDto.getTitle());
        System.out.println("DueDate: " + taskDto.getDueDate());

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus() != null ? taskDto.getStatus() : TaskStatus.PENDING);
        task.setDueDate(taskDto.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        System.out.println("=== DEBUG: Task saved with ID: " + savedTask.getId() + " ===");

        return savedTask;
    }

    @Override
    public Task updateTask(Long id, TaskDto taskDto, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("У вас нет прав для редактирования этой задачи");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(taskDto.getDueDate());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("У вас нет прав для удаления этой задачи");
        }
        taskRepository.delete(task);
    }

    @Override
    public Task getTaskById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("У вас нет прав для просмотра этой задачи");
        }

        return task;
    }

    @Override
    public Page<Task> getUserTasks(User user, Pageable pageable) {
        System.out.println("=== DEBUG: Getting tasks for user: " + user.getUsername() + " ===");
        Page<Task> tasks = taskRepository.findByUser(user, pageable);
        System.out.println("=== DEBUG: Found " + tasks.getTotalElements() + " tasks ===");
        return tasks;
    }

    @Override
    public Task toggleTaskStatus(Long id, User user) {
        Task task = getTaskById(id, user);
        task.setStatus(task.getStatus() == TaskStatus.PENDING ?
                TaskStatus.COMPLETED : TaskStatus.PENDING);
        return taskRepository.save(task);
    }
}