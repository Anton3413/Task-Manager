package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class ResponseTaskDtoMapper implements Mapper<ResponseTaskDto, Task> {
    @Override
    public ResponseTaskDto toDto(Task entity) {
       return ResponseTaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public Task toEntity(ResponseTaskDto responseTaskDto) {
        return null;
    }
}
