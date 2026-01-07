package com.anton3413.taskmanager.dto.task;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.validation.annotation.FutureOrPresent;
import com.anton3413.taskmanager.validation.annotation.UniqueTaskTitle;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@UniqueTaskTitle(message = "Task with this title already exists")
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 2000, message = "Description is too long (max 2000 chars)")
    private String description;

    private Status status;

    @FutureOrPresent(message = "The due date cannot be in the past")
    private LocalDateTime dueDate;
}