package com.anton3413.taskmanager.validation.annotation;

import com.anton3413.taskmanager.validation.validator.UniqueTaskTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueTaskTitleValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTaskTitle {
    String message() default "Title must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}