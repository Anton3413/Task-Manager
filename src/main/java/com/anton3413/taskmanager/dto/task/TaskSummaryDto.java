package com.anton3413.taskmanager.dto.task;


import com.anton3413.taskmanager.model.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class TaskSummaryDto {

    private Long id;

    private String title;

    private Status status;

    private LocalDateTime dueDate;
}
