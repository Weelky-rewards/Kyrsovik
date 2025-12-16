package com.example.todolist.repository;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser(User user, Pageable pageable);
    List<Task> findByUserAndStatus(User user, com.example.todolist.model.TaskStatus status);
    long countByUser(User user);
}