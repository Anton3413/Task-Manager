package com.anton3413.taskmanager.dto.task;


import com.anton3413.taskmanager.model.Status;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTaskDto {

    private Long id;

    private String title;

    private String description;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime dueDate;

}
