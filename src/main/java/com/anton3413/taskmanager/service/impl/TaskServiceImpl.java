package com.anton3413.taskmanager.service.impl;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.SecurityService;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.service.TaskService;
import com.anton3413.taskmanager.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final SecurityService securityService;
    private final String EXCEPTION_MESSAGE_ENTITY_NOT_FOUND = "The task with id %s doesn't exist," +
            " might have been removed, or is temporarily unavailable.";

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findByIdAndUser_Username(taskId, securityService.getCurrentUsername())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(EXCEPTION_MESSAGE_ENTITY_NOT_FOUND, taskId)));
    }

    @Override
    public void deleteById(Long taskId) {
        Task task = findById(taskId);
        taskRepository.delete(task);
    }

    @Override
    public void save(Task task) {
        User user = userService.findByUsername(securityService.getCurrentUsername());
        task.setUser(user);
        taskRepository.save(task);
    }

    public void save(Task task, String username) {
        User user = userService.findByUsername(username);
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll(Sort sort) {
        return taskRepository.findAllByUser_Username(securityService.getCurrentUsername(), sort);
    }

    @Override
    public boolean existsByTitleIgnoreCase(String title) {
        return taskRepository.existsByTitleIgnoreCaseAndUser_Username(title, securityService.getCurrentUsername());
    }

    @Override
    public Optional<Task> findTaskByTitleIgnoreCase(String title) {
        return taskRepository.findTaskByTitleIgnoreCaseAndUser_Username(title, securityService.getCurrentUsername());
    }

    @Override
    public void updateStatus(Long taskId, String status) {
        Task task = findById(taskId);
        task.setStatus(Status.valueOf(status));
    }
}
