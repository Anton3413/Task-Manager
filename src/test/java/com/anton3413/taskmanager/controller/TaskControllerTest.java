package com.anton3413.taskmanager.controller;


import com.anton3413.taskmanager.dto.task.CreateTaskDto;
import com.anton3413.taskmanager.dto.task.ResponseTaskDto;
import com.anton3413.taskmanager.dto.task.TaskSummaryDto;
import com.anton3413.taskmanager.mapper.TaskMapper;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc()
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TaskService taskService;

    @MockitoBean
    TaskMapper taskMapper;

    private static final Long TASK_ID = 1L;

    @Test
    void displayAllTasks_shouldPerformsCorrectly() throws Exception {
        Task task = Task.builder().id(TASK_ID).build();

        when(taskService.findAll(any(Sort.class))).thenReturn(List.of(task));
        when(taskMapper.fromEntityToTaskSummaryDto(task)).thenReturn(TaskSummaryDto.builder().build());

        mockMvc.perform(get("/tasks")
                        .param("sortField","id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tasks",hasSize(1)))
                .andExpect(model().attribute("sortField","id"))
                .andExpect(model().attribute("sortDir","asc"))
                .andExpect(model().attributeExists("reverseSortDir"))
                .andExpect(view().name("tasks"));
    }

    @Test
    void displayAllTasks_shouldPerformsCorrectlyEvenWithIncorrectParams() throws Exception {
        Task task = Task.builder().id(TASK_ID).build();

        when(taskService.findAll(any(Sort.class))).thenReturn(List.of(task));
        when(taskMapper.fromEntityToTaskSummaryDto(task)).thenReturn(TaskSummaryDto.builder().build());

        mockMvc.perform(get("/tasks")
                        .param("sortField","incorrect")
                        .param("sortDir", "error!"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tasks",hasSize(1)))
                .andExpect(model().attribute("sortField","id"))
                .andExpect(model().attribute("sortDir","asc"))
                .andExpect(model().attributeExists("reverseSortDir"))
                .andExpect(view().name("tasks"));
    }

    @Test
    void displayTaskDetails_shouldReturnPageTaskInfoPage() throws Exception {

        Task entityFromRepository = Task.builder().id(1L).build();
        ResponseTaskDto responseTaskDto = ResponseTaskDto.builder()
                .id(TASK_ID)
                .build();

        when(taskService.findById(TASK_ID)).thenReturn(entityFromRepository);
        when(taskMapper.fromEntityToResponseTaskDto(entityFromRepository)).thenReturn(responseTaskDto);

        mockMvc.perform(get("/tasks/{id}",TASK_ID))
                .andExpect(model().attributeExists("task"))
                .andExpect(view().name("task-details"));

        verify(taskService).findById(TASK_ID);
        verify(taskMapper).fromEntityToResponseTaskDto(any(Task.class));
    }

    @Test
    void displayTaskDetails_shouldReturnBadRequestAndShowErrorPage() throws Exception {
        mockMvc.perform(get("/tasks/wrong-id"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException()
                            instanceof MethodArgumentTypeMismatchException);}
                )
                .andExpect(model().attributeExists("statusCode"))
                .andExpect(model().attributeExists("errorTitle"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("errors/common-error"));
    }

    @Test
    void displayTaskCreatingPage_performsCorrectly() throws Exception{
        mockMvc.perform(get("/tasks/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-task"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void saveNewTaskFromForm_shouldSaveNewTaskWhenValidationSuccess() throws Exception {

        final String dateTimeStr = "2026-12-31T23:59:59";

        when(taskMapper.fromCreateTaskDtoToEntity(any(CreateTaskDto.class)))
                .thenReturn(Task.builder().build());
        doNothing().when(taskService).save(any(Task.class));

        mockMvc.perform(post("/tasks/new")
                .param("title","example Title")
                .param("description", "Example Description")
                .param("status","IN_PROGRESS")
                .param("dueDate",dateTimeStr)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskMapper).fromCreateTaskDtoToEntity(any());
        verify(taskService).save(any());
    }

}
