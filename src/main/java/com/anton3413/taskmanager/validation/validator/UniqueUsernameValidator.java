package com.anton3413.taskmanager.validation.validator;

import com.anton3413.taskmanager.service.UserService;
import com.anton3413.taskmanager.validation.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(password);
    }
}
