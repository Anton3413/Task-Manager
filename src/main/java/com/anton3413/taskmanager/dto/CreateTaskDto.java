package com.anton3413.taskmanager.dto;

import com.anton3413.taskmanager.model.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class CreateTaskDto {


    private String title;

    @Size(max = 500)
    private String description;

    private Status status;

    @Future
    private LocalDateTime dueDate;
}
