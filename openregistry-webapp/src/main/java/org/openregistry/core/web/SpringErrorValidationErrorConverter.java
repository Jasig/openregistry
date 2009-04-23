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

    /**
     * COnverts the validation errors returned from the {@link org.openregistry.core.service.PersonService} into
     * Spring {@link org.springframework.validation.Errors}.
     *
     * @param validationErrors the errors provided by the {@link org.openregistry.core.service.PersonService}
     * @param errors an instance of Spring's {@link org.springframework.validation.Errors}
     */
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
