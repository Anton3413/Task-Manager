package com.anton3413.taskmanager.integration.controller;

import com.anton3413.taskmanager.integration.BaseIT;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.repository.UserRepository;
import com.anton3413.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

public class TaskControllerIntegrationTest extends BaseIT {

    private static final String TASK_TITLE = "Task title";
    private static final String TASK_DESCRIPTION = "Task Description";
    private static final String USER_USERNAME = "user";

    private MockMvc mockMvc;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        taskRepository.deleteAllInBatch();
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
                        .springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = USER_USERNAME)
    void displayAllTasks_sortingShouldWorksCorrectly() throws Exception {

        User testUser = User.builder()
                .username("user")
                .password("password")
                .email("test@mail.com")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.saveAndFlush(testUser);

        Task firstTask = Task.builder()
                .title(TASK_TITLE)
                .description(TASK_DESCRIPTION)
                .dueDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .user(testUser)
                .status(Status.IN_PROGRESS)
                .build();

        Task secondTask = Task.builder()
                .title(TASK_TITLE + "qwerty")
                .description(TASK_DESCRIPTION)
                .dueDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .user(testUser)
                .status(Status.IN_PROGRESS)
                .build();

        taskRepository.saveAllAndFlush(List.of(firstTask,secondTask));

        mockMvc.perform(get("/tasks")
                        .param("sortField","title")
                        .param("sortDir", "asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("tasks", contains(
                        hasProperty("title", is(TASK_TITLE)),
                        hasProperty("title", is(TASK_TITLE + "qwerty"))
                )));

    }
}
