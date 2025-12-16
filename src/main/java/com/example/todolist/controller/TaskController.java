package com.example.todolist.controller;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;
    private User getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                return null;
            }

            String username = auth.getName();
            return userService.findByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping
    public String listTasks(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {

        User user = getCurrentUser();

        if (user == null) {
            return "redirect:/auth/login";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Task> taskPage = taskService.getUserTasks(
                user,
                PageRequest.of(currentPage - 1, pageSize)
        );

        model.addAttribute("taskPage", taskPage);
        model.addAttribute("task", new TaskDto());
        model.addAttribute("currentUser", user);

        return "tasks";
    }

    @PostMapping
    public String createTask(
            @Valid @ModelAttribute("task") TaskDto taskDto,
            BindingResult bindingResult,
            Model model) {

        User user = getCurrentUser();

        if (user == null) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            Page<Task> taskPage = taskService.getUserTasks(user, PageRequest.of(0, 10));
            model.addAttribute("taskPage", taskPage);
            model.addAttribute("currentUser", user);
            return "tasks";
        }

        try {
            taskService.createTask(taskDto, user);
            return "redirect:/tasks?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при создании задачи: " + e.getMessage());
            Page<Task> taskPage = taskService.getUserTasks(user, PageRequest.of(0, 10));
            model.addAttribute("taskPage", taskPage);
            model.addAttribute("currentUser", user);
            return "tasks";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        User user = getCurrentUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        try {
            Task task = taskService.getTaskById(id, user);
            TaskDto taskDto = new TaskDto();
            taskDto.setId(task.getId());
            taskDto.setTitle(task.getTitle());
            taskDto.setDescription(task.getDescription());
            taskDto.setPriority(task.getPriority());
            taskDto.setStatus(task.getStatus());
            taskDto.setDueDate(task.getDueDate());

            model.addAttribute("task", taskDto);
            return "edit-task";
        } catch (Exception e) {
            return "redirect:/tasks?error=not_found";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTask(
            @PathVariable Long id,
            @Valid @ModelAttribute("task") TaskDto taskDto,
            BindingResult bindingResult,
            Model model) {

        User user = getCurrentUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        if (bindingResult.hasErrors()) {
            return "edit-task";
        }

        try {
            taskService.updateTask(id, taskDto, user);
            return "redirect:/tasks?success=updated";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при обновлении задачи: " + e.getMessage());
            return "edit-task";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        try {
            taskService.deleteTask(id, user);
            return "redirect:/tasks?success=deleted";
        } catch (Exception e) {
            return "redirect:/tasks?error=delete_failed";
        }
    }

    @GetMapping("/toggle/{id}")
    public String toggleTaskStatus(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) {
            return "redirect:/auth/login";
        }

        try {
            taskService.toggleTaskStatus(id, user);
            return "redirect:/tasks";
        } catch (Exception e) {
            return "redirect:/tasks?error=toggle_failed";
        }
    }
}