package com.anton3413.taskmanager.validation.annotation;

import com.anton3413.taskmanager.validation.validator.UniqueTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueTitleValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "Title must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}