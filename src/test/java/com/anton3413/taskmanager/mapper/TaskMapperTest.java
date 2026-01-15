package com.anton3413.taskmanager.mapper;


import com.anton3413.taskmanager.dto.task.CreateTaskDto;
import com.anton3413.taskmanager.dto.task.EditTaskDto;
import com.anton3413.taskmanager.dto.task.ResponseTaskDto;
import com.anton3413.taskmanager.dto.task.TaskSummaryDto;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class TaskMapperTest {

    private TaskMapper taskMapper;
    private static final Long ID = 66L;
    private static final String TITLE = "Demo_title";
    private static final String DESCRIPTION = "Task description";
    private static final Status STATUS = Status.IN_PROGRESS;
    private static final LocalDateTime DUE_DATE = LocalDateTime.now().plusDays(7);

    @BeforeEach
    void initTaskMapper() {
        taskMapper = new TaskMapper();
    }

    @Test
    void fromCreateTaskDtoToEntityTest() {
        CreateTaskDto sourceObject = CreateTaskDto.builder()
                .title(TITLE).description(DESCRIPTION)
                .status(STATUS).dueDate(DUE_DATE)
                .build();

        Task targetObject = taskMapper.fromCreateTaskDtoToEntity(sourceObject);

        assertNull(targetObject.getId());
        assertEquals(sourceObject.getTitle(), targetObject.getTitle());
        assertEquals(sourceObject.getDescription(), targetObject.getDescription());
        assertEquals(sourceObject.getStatus(), targetObject.getStatus());
        assertEquals(sourceObject.getDueDate(), targetObject.getDueDate());
    }

    @Test
    void fromEntityToEditTaskDtoTest(){
        Task sourceObject = buildTaskEntity();

        EditTaskDto targetObject = taskMapper.fromEntityToEditTaskDto(sourceObject);

        assertEquals(sourceObject.getId(), targetObject.getId());
        assertEquals(sourceObject.getTitle(), targetObject.getTitle());
        assertEquals(sourceObject.getDescription(), targetObject.getDescription());
        assertEquals(sourceObject.getStatus(), targetObject.getStatus());
        assertThat(targetObject.getCreatedAt())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
        assertEquals(sourceObject.getDueDate(), targetObject.getDueDate());
    }

    @Test
    void fromEditTaskDtoToEntityTest(){
        EditTaskDto sourceObject = EditTaskDto.builder()
                .id(ID).title(TITLE).description(DESCRIPTION)
                .status(STATUS).dueDate(DUE_DATE)
                .build();

        Task targetObject = taskMapper.fromEditTaskDtoToEntity(sourceObject);

        assertEquals(sourceObject.getId(), targetObject.getId());
        assertEquals(sourceObject.getTitle(), targetObject.getTitle());
        assertEquals(sourceObject.getDescription(), targetObject.getDescription());
        assertEquals(sourceObject.getStatus(), targetObject.getStatus());
        assertEquals(sourceObject.getDueDate(), targetObject.getDueDate());
    }

    @Test
    void fromEntityToResponseTaskDto(){
        Task sourceObject = buildTaskEntity();

        ResponseTaskDto targetObject = taskMapper.fromEntityToResponseTaskDto(sourceObject);

        assertEquals(sourceObject.getId(), targetObject.getId());
        assertEquals(sourceObject.getTitle(), targetObject.getTitle());
        assertEquals(sourceObject.getDescription(), targetObject.getDescription());
        assertEquals(sourceObject.getStatus(), targetObject.getStatus());
        assertEquals(sourceObject.getCreatedAt(), targetObject.getCreatedAt());
        assertEquals(sourceObject.getDueDate(), targetObject.getDueDate());
    }

    @Test
    void fromEntityToTaskSummaryDto(){

        Task sourceObject = buildTaskEntity();

        TaskSummaryDto targetObject = taskMapper.fromEntityToTaskSummaryDto(sourceObject);

        assertEquals(sourceObject.getId(), targetObject.getId());
        assertEquals(sourceObject.getTitle(), targetObject.getTitle());
        assertEquals(sourceObject.getStatus(), targetObject.getStatus());
        assertEquals(sourceObject.getDueDate(), targetObject.getDueDate());
    }

    private Task buildTaskEntity() {
        return Task.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .status(STATUS)
                .dueDate(DUE_DATE)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
