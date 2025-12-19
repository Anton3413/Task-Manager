package com.anton3413.taskmanager.service.impl;

import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.dto.TaskSummaryDto;
import com.anton3413.taskmanager.mapper.CreateTaskDtoMapper;
import com.anton3413.taskmanager.mapper.ResponseTaskDtoMapper;
import com.anton3413.taskmanager.mapper.TaskSummaryDtoMapper;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskSummaryDtoMapper taskSummaryDtoMapper;
    private final CreateTaskDtoMapper createTaskDtoMapper;
    private final ResponseTaskDtoMapper responseTaskDtoMapper;

    @Override
    public ResponseTaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return responseTaskDtoMapper.toDto(task);
    }

    @Override
    public void deleteById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void save(CreateTaskDto createTaskDto) {
        taskRepository.save(createTaskDtoMapper.toEntity(createTaskDto));
    }

    @Override
    public List<TaskSummaryDto> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskSummaryDtoMapper::toDto)
                .toList();
    }

    @Override
    public boolean existsByTitleIgnoreCase(String title){

      return taskRepository.existsByTitleIgnoreCase(title);
    }
}
