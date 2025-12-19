package com.anton3413.taskmanager.config;

import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskService taskService;

    private static final String EXAMPLE_TASK_TITLE = "Demo Task";
    private static final String EXAMPLE_TASK_DESCRIPTION = """
    This is a demonstration task. It is automatically initialized \
    when the application starts. \
    You can interact with it like any other task, including deleting it. :)
    """;

    private static final LocalDateTime EXAMPLE_TASK_DEADLINE_DATE = LocalDateTime
            .now()
            .plusMonths(6);


    @Override
    public void run(String... args) throws Exception {

        if(!taskService.existsByTitleIgnoreCase(EXAMPLE_TASK_TITLE)){
             CreateTaskDto demoTask = CreateTaskDto.builder()
                    .title(EXAMPLE_TASK_TITLE)
                    .description(EXAMPLE_TASK_DESCRIPTION)
                    .status(Status.NEW)
                    .dueDate(EXAMPLE_TASK_DEADLINE_DATE)
                    .build();

             taskService.save(demoTask);
        }
    }
}
