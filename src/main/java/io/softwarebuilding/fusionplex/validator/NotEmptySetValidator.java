package io.softwarebuilding.fusionplex.validator;

import io.softwarebuilding.fusionplex.validator.annotation.NotEmptySet;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NotEmptySetValidator implements ConstraintValidator<NotEmptySet, Set<?>> {

    @Override
    public boolean isValid( final Set<?> value, final ConstraintValidatorContext context ) {
        return value != null && !value.isEmpty();
    }

}
