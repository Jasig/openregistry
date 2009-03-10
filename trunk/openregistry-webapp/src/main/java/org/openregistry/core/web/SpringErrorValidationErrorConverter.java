package org.openregistry.core.web;

import org.openregistry.core.service.ValidationError;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * Converts the OpenRegistry Validation Errors to the Spring Validation Errors.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringErrorValidationErrorConverter {

    public void convertValidationErrors(final List<ValidationError> validationErrors, final Errors errors) {

        for (final ValidationError validationError : validationErrors) {
            if (validationError.getField() == null) {
                errors.reject(validationError.getCode(), validationError.getArguments(), null);
            } else {
                errors.rejectValue(validationError.getField(), validationError.getCode(), validationError.getArguments(), null);
            }
        }
    }
}
