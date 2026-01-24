package com.anton3413.taskmanager.config;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.service.TaskService;
import com.anton3413.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final TaskService taskService;
    private final UserService userService;
    private final String EXAMPLE_TASK_DESCRIPTION = """
    This is a demonstration task. It is automatically initialized \
    when the application starts. \
    You can interact with it like any other task, including deleting it. :)
    """;

    @Override
    public void run(String... args) {
        initData("user", "user@example.com", "Demo Task");
        initData("admin", "admin@example.com", "Second demo Task");
    }

    private void initData(String username, String email, String taskTitle) {
        if (userService.existsByUsername(username)) {
            return;
        }
        User user = User.builder()
                .username(username)
                .password("{noop}123")
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
        userService.save(user);

        Task task = Task.builder()
                .title(taskTitle)
                .description(EXAMPLE_TASK_DESCRIPTION)
                .status(Status.NEW)
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusWeeks(1))
                .build();

        taskService.save(task, username);
    }
}
