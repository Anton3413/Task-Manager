package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.dto.EditTaskDto;
import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.dto.TaskSummaryDto;
import com.anton3413.taskmanager.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public Task fromCreateTaskDtoToEntity(CreateTaskDto createTaskDto) {
        return Task.builder()
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .status(createTaskDto.getStatus())
                .createdAt(LocalDateTime.now())
                .dueDate(createTaskDto.getDueDate())
                .build();
    }

    public EditTaskDto fromEntityToEditTaskDto(Task task) {
        return EditTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description((task.getDescription()))
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .build();
    }
    public Task fromEditTaskDtoToEntity(EditTaskDto dto) {
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description((dto.getDescription()))
                .createdAt(dto.getCreatedAt())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .build();
    }
    public ResponseTaskDto fromEntityToResponseTaskDto(Task entity) {
        return ResponseTaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }
    public TaskSummaryDto fromEntityToTaskSummaryDto(Task task) {
        return TaskSummaryDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .build();
    }
}
