package com.anton3413.taskmanager.controller;


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


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
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
    void showAllTasks_shouldPerformsCorrectly() throws Exception {
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
    void showAllTasks_shouldPerformsCorrectlyEvenWithIncorrectParams() throws Exception {
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

}
