package org.openregistry.core.domain.validation;

import org.openregistry.core.domain.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Validates the gender supplied against a list of allowed values.
 *
 * @version $Revision$ $Date$
 * @since 1.0
 */
public final class GenderConstraintValidator implements ConstraintValidator<Gender,String> {

    private String[] acceptableValues = new String[] {"M", "F"};

    @Override
    public void initialize(final Gender gender) {
        // nothing to do
    }

    @Override
    public boolean isValid(final String string, final ConstraintValidatorContext constraintValidatorContext) {       
        return string == null || Arrays.asList(acceptableValues).contains(string);
    }
}
