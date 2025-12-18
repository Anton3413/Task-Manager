package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.TaskSummaryDto;
import com.anton3413.taskmanager.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskSummaryDtoMapper implements Mapper<TaskSummaryDto, Task> {
    @Override
    public TaskSummaryDto toDto(Task task) {
        return TaskSummaryDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .build();
    }

    @Override
    public Task toEntity(TaskSummaryDto taskSummaryDto) {
        return null;
    }

}
