package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.EditTaskDto;
import com.anton3413.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class EditTaskDtoMapper implements Mapper<EditTaskDto, Task> {
    @Override
    public EditTaskDto toDto(Task task) {
      return EditTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description((task.getDescription()))
                .status(task.getStatus())
                .dueDate(task.getDueDate())
              .createdAt(task.getCreatedAt())
                .build();
    }

    @Override
    public Task toEntity(EditTaskDto dto) {
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description((dto.getDescription()))
                .createdAt(dto.getCreatedAt())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .build();
    }
}
