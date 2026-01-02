package com.anton3413.taskmanager.validation.validator;

import com.anton3413.taskmanager.validation.annotation.FutureOrPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FutureOrPresentValidator implements ConstraintValidator<FutureOrPresent, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        return !localDateTime.isBefore(now);
    }
}
