package com.anton3413.taskmanager.integration.controller;

import com.anton3413.taskmanager.integration.BaseIT;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WithMockUser(username = "user")
@Transactional
public class TaskControllerIntegrationTest extends BaseIT {

    private static final String TASK_TITLE = "Task title";
    private static final String TASK_DESCRIPTION = "Task Description";
    private static final String USER_USERNAME = "user";

    private MockMvc mockMvc;
    private User testUser;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
                        .springSecurity())
                .build();
        testUser = constructAndSaveTestUser();
    }

    @Test
    void displayAllTasks_sortingShouldWorksCorrectly() throws Exception {

        constructAndSaveTestTask(TASK_TITLE);
        constructAndSaveTestTask(TASK_TITLE+ "qwerty");

        mockMvc.perform(get("/tasks")
                        .param("sortField","title")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tasks", contains(
                        hasProperty("title", is(TASK_TITLE)),
                        hasProperty("title", is(TASK_TITLE + "qwerty"))
                )));
    }

    @Test
    void saveNewTaskFromForm_ValidationErrorTaskTitleAlreadyExistsAndIncorrectDueDate() throws Exception{

        final String taskTitleToValidate = TASK_TITLE.toUpperCase() + "   " ;

        constructAndSaveTestTask(TASK_TITLE);

        mockMvc.perform(post("/tasks/new")
                        .param("title",taskTitleToValidate)
                        .param("description",TASK_DESCRIPTION)
                        .param("status", Status.NEW.toString())
                        .param("dueDate", LocalDateTime.now().minusDays(2).toString())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("create-task"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors("task", "title"))
                .andExpect(model().attributeHasFieldErrors("task","dueDate"));
    }

    @Test
    void editTask_shouldSuccessfullyEditExistingTask() throws Exception {

        final String newTitle = "Hello, I am a new title!";
        final String newDescription = "Hello, I am a new description!";
        final String newDeadline = LocalDateTime.now().plusMonths(10)
                .truncatedTo(ChronoUnit.MINUTES).toString();

        final Long taskId = constructAndSaveTestTask(TASK_TITLE).getId();

        mockMvc.perform(post("/tasks/edit")
                .with(csrf())
                .param("id", taskId.toString())
                .param("title",newTitle)
                .param("description", newDescription)
                .param("status", Status.DONE.toString())
                .param("dueDate",newDeadline)
                .param("created_at", LocalDateTime.now().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        Task editedTask = taskRepository.findById(taskId).get();

        assertEquals(newTitle, editedTask.getTitle());
        assertEquals(newDescription, editedTask.getDescription());
        assertEquals(newDeadline, editedTask.getDueDate().toString());
    }

    private User constructAndSaveTestUser(){
       return userRepository.saveAndFlush(User.builder()
               .username(USER_USERNAME).password("password")
               .email("test@mail.com")
               .createdAt(LocalDateTime.now())
               .build());
    }

    private Task constructAndSaveTestTask(String taskTitle){
        return taskRepository.saveAndFlush(Task.builder()
                        .title(taskTitle)
                        .description(TASK_DESCRIPTION)
                        .dueDate(LocalDateTime.now().plusMonths(1))
                        .createdAt(LocalDateTime.now())
                        .user(testUser)
                        .status(Status.IN_PROGRESS)
                        .build());
    }
}
