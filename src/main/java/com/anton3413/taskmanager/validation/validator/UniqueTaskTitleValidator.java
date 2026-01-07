package com.anton3413.taskmanager.validation.validator;

import com.anton3413.taskmanager.dto.task.CreateTaskDto;
import com.anton3413.taskmanager.dto.task.EditTaskDto;

import com.anton3413.taskmanager.service.TaskService;
import com.anton3413.taskmanager.validation.annotation.UniqueTaskTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueTaskTitleValidator implements ConstraintValidator<UniqueTaskTitle, Object> {

    private final TaskService taskService;

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean isValid;

        if (object instanceof EditTaskDto) {
            isValid = validateEditTaskDto((EditTaskDto) object);
        } else if (object instanceof CreateTaskDto) {
            isValid = validateCreateTaskDto((CreateTaskDto) object);
        } else {
            return true;
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("title")
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean validateCreateTaskDto(CreateTaskDto dto) {
        return !taskService.existsByTitleIgnoreCase(dto.getTitle().trim());
    }

    private boolean validateEditTaskDto(EditTaskDto dto) {
        return taskService.findTaskByTitleIgnoreCase(dto.getTitle().trim())
                .map(existingTask -> existingTask.getId().equals(dto.getId()))
                .orElse(true);
    }
}