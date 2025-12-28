package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task findById(Long id);

    void deleteById(Long id);

    void save(Task task);

    List<Task> findAll(Sort sort);

    boolean existsByTitleIgnoreCase(String title);

    Optional<Task> findTaskByTitleIgnoreCase(String title);

    @Transactional
    void updateStatus(Long taskId, String status);
}
