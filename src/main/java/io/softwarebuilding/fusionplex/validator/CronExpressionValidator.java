package io.softwarebuilding.fusionplex.validator;

import io.softwarebuilding.fusionplex.validator.annotation.ValidCronExpression;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.quartz.CronExpression;

import java.text.ParseException;

public class CronExpressionValidator implements ConstraintValidator<ValidCronExpression, String> {

    @Override
    public void initialize(final ValidCronExpression constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String cronExpression, final ConstraintValidatorContext context) {

        if (cronExpression == null || cronExpression.isEmpty()) {
            return true;
        }

        try {
            new CronExpression(cronExpression);
            return true;
        } catch (final ParseException exception) {
            return false;
        }
    }
}
