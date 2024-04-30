package io.softwarebuilding.fusionplex.validator.annotation;

import io.softwarebuilding.fusionplex.validator.CronExpressionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CronExpressionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCronExpression {

    String message() default "Invalid Cron Expression";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
