package com.beautysalon.api.v1.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartBeforeEndValidator.class)
@ReportAsSingleViolation
public @interface StartBeforeEnd {
    String message() default "Start date must be before end date";

    String start() default "startDatetime";

    String end() default "endDatetime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}