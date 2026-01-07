package com.anton3413.taskmanager.dto.task;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditTaskDto extends CreateTaskDto {

    private Long id;

    private LocalDateTime createdAt;

}
