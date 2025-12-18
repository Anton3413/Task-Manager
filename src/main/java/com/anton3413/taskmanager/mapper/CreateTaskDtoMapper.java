package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateTaskDtoMapper implements Mapper<CreateTaskDto, Task> {

    @Override
    public  CreateTaskDto toDto(Task entity) {
        return null;
    }

    @Override
    public Task toEntity(CreateTaskDto createTaskDto) {
        return Task.builder()
                .title(createTaskDto.getTitle())
                .description(createTaskDto.getDescription())
                .status(createTaskDto.getStatus())
                .createdAt(LocalDateTime.now())
                .dueDate(createTaskDto.getDueDate())
                .build();
    }

}
