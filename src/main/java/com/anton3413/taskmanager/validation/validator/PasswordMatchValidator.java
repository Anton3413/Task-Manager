package com.anton3413.taskmanager.validation.validator;

import com.anton3413.taskmanager.dto.user.CreateUserDto;
import com.anton3413.taskmanager.validation.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CreateUserDto> {
    @Override
    public boolean isValid(CreateUserDto dto, ConstraintValidatorContext context) {
        return dto.getPassword() != null && dto.getPassword().equals(dto.getConfirmPassword());
    }
}