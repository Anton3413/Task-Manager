package com.anton3413.taskmanager.dto;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.validation.annotation.FutureOrPresent;
import com.anton3413.taskmanager.validation.annotation.Unique;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class CreateTaskDto {

    @Size(max = 50, message = "Title cannot exceed 50 characters")
    @NotBlank(message = "Title is required")
    @Unique(message = "Task with this title already exists")
    private String title;

    @Size(max = 1200, message = "Description is too long (max 1200 chars)")
    private String description;

    private Status status;

    @FutureOrPresent(message = "The due date cannot be in the past")
    private LocalDateTime dueDate;
}
