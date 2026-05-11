package edu.brooklyn.cisc3130.taskboard.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PriorityValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriority {
    String message() default "Invalid priority. Must be LOW, MEDIUM, or HIGH";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}