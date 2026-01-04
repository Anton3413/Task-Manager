package com.anton3413.taskmanager.service.impl;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.repository.UserRepository;
import com.anton3413.taskmanager.service.TaskService;
import com.anton3413.taskmanager.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final String EXCEPTION_MESSAGE_ENTITY_NOT_FOUND = "The task with id %s doesn't exist," +
            " might have been removed, or is temporarily unavailable.";

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException(String.format(EXCEPTION_MESSAGE_ENTITY_NOT_FOUND,id)));
    }

    @Override
    public void deleteById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new EntityNotFoundException(String.format(EXCEPTION_MESSAGE_ENTITY_NOT_FOUND,id));
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void save(Task task, String username) {
        User user = userService.findByUsername(username);
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll(Sort sort) {
        return taskRepository.findAll(sort);
    }

    @Override
    public boolean existsByTitleIgnoreCase(String title){

      return taskRepository.existsByTitleIgnoreCase(title);
    }

    @Override
    public Optional<Task> findTaskByTitleIgnoreCase(String title){
      return  taskRepository.findTaskByTitleIgnoreCase(title);
    }

    @Override
    public void updateStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException(String.format(EXCEPTION_MESSAGE_ENTITY_NOT_FOUND,taskId)));
        task.setStatus(Status.valueOf(status));
    }

    @Override
    public List<Task> findAllByUserUsername(String username, Sort sort) {

        return taskRepository.findAllByUser_Username(username,sort);
    }
}
