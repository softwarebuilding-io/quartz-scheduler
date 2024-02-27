package io.softwarebuilding.fusionplex.validator.annotation;

import io.softwarebuilding.fusionplex.validator.NotEmptySetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotEmptySetValidator.class)
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface NotEmptySet {

    String message() default "The set cannot be empty.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
