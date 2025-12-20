package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task findById(Long id);

    void deleteById(Long id);

    void save(Task task);

    List<Task> findAll();

    boolean existsByTitleIgnoreCase(String title);

    Optional<Task> findTaskByTitleIgnoreCase(String title);

}
