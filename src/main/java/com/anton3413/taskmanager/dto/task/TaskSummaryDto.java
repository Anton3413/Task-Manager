package com.anton3413.taskmanager.dto.task;


import com.anton3413.taskmanager.model.Status;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSummaryDto {

    private Long id;

    private String title;

    private Status status;

    private LocalDateTime dueDate;
}
