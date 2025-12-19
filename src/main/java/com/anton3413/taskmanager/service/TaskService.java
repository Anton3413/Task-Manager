package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.dto.TaskSummaryDto;

import java.util.List;

public interface TaskService {

    ResponseTaskDto findById(Long id);

    void deleteById(Long id);

    void save(CreateTaskDto createTaskDto);

    List<TaskSummaryDto> findAll();

    boolean existsByTitleIgnoreCase(String title);

}
