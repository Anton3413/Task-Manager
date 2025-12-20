package com.anton3413.taskmanager.service.impl;

import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll()
                .stream()
                .toList();
    }

    @Override
    public boolean existsByTitleIgnoreCase(String title){

      return taskRepository.existsByTitleIgnoreCase(title);
    }

    @Override
    public Optional<Task> findTaskByTitleIgnoreCase(String title){
      return  taskRepository.findTaskByTitleIgnoreCase(title);
    }
}
