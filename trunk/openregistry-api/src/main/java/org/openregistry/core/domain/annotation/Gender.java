package org.openregistry.core.domain.annotation;

import org.openregistry.core.domain.validation.GenderConstraintValidator;
import org.openregistry.core.domain.validation.RequiredFieldConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Constraint(validatedBy = GenderConstraintValidator.class)

public @interface Gender {

    String message() default "{org.openregistry.core.domain.annotation.Gender.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
