package com.anton3413.taskmanager.config;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.service.TaskService;
import com.anton3413.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskService taskService;
    private final UserService userService;

    private final String EXAMPLE_TASK_TITLE = "Demo Task";
    private final String EXAMPLE_TASK_DESCRIPTION = """
    This is a demonstration task. It is automatically initialized \
    when the application starts. \
    You can interact with it like any other task, including deleting it. :)
    """;
    private final String EXAMPLE_USER_USERNAME = "user";

    @Override
    public void run(String... args) throws Exception {

        if(!userService.existsByUsername(EXAMPLE_USER_USERNAME)){
            insertExampleUser();
        }
        /*if(!taskService.existsByTitleIgnoreCase(EXAMPLE_TASK_TITLE)){
             insertExampleTask();
        }*/
    }
    private void insertExampleUser(){
        User demoUser = User.builder()
                .username(EXAMPLE_USER_USERNAME)
                .password("{noop}123")
                .email("email@example.com")
                .createdAt(LocalDateTime.now())
                .build();
        User savedUser =  userService.save(demoUser);

        Task demoTask = Task.builder()
                .title(EXAMPLE_TASK_TITLE)
                .description(EXAMPLE_TASK_DESCRIPTION)
                .createdAt(LocalDateTime.now())
                .status(Status.NEW)
                .dueDate(LocalDateTime.now().plusMonths(6))
                .build();
        taskService.save(demoTask,"user");
    }
}
