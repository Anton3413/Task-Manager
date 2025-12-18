package com.anton3413.taskmanager.dto;


import com.anton3413.taskmanager.model.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class ResponseTaskDto {

    private Long id;

    private String title;

    private String description;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime dueDate;

}
