package edu.brooklyn.cisc3130.taskboard.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator
        implements ConstraintValidator<ValidPriority, String> {

    private static final List<String> VALID_PRIORITIES =
            Arrays.asList("LOW", "MEDIUM", "HIGH");

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return VALID_PRIORITIES.contains(value.toUpperCase());
    }
}