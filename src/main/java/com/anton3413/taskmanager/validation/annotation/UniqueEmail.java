package com.anton3413.taskmanager.validation.annotation;

import com.anton3413.taskmanager.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "An account with this email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}