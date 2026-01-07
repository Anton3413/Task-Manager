package com.anton3413.taskmanager.validation.annotation;

import com.anton3413.taskmanager.validation.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "User with that name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}