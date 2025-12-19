package com.anton3413.taskmanager.validation.validator;

import com.anton3413.taskmanager.repository.TaskRepository;

import com.anton3413.taskmanager.validation.annotation.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueTitleValidator implements ConstraintValidator<Unique, String> {

    private final TaskRepository taskRepository;

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {

        return !taskRepository.existsByTitleIgnoreCase(title.trim());
    }
}