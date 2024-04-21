package com.beautysalon.api.v1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class StartBeforeEndValidator
        implements ConstraintValidator<StartBeforeEnd, Object> {
    private String start;
    private String end;

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        this.start = constraintAnnotation.start();
        this.end = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            if (obj == null)
                return true;

            final Field startField = obj.getClass().getDeclaredField(start);
            final Field endField = obj.getClass().getDeclaredField(end);
            final LocalDateTime startDatetime = (LocalDateTime) startField.get(obj);
            final LocalDateTime endDatetime = (LocalDateTime) endField.get(obj);

            return startDatetime.isBefore(endDatetime);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }
}
