package com.anton3413.taskmanager.validation.annotation;

import com.anton3413.taskmanager.validation.validator.FutureOrPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureOrPresentValidator.class)
@Documented
public @interface FutureOrPresent {
    String message() default "The date must be in the future or present (at least this minute)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}