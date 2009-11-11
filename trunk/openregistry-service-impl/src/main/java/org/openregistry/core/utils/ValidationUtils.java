package org.openregistry.core.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Various static validation helpers
 *
 * @since 1.0
 */
public final class ValidationUtils {

    //Prevents instantiation
    private ValidationUtils() {
    }

    /**
     * Build a String representation of a set of provided <code>ConstraintViolation</code>s
     *
     * @param validationErrors
     * @return String representation of a given collection constriant violations
     *         or null if the set is empty
     * @throws NullPointerException if passed in set reference is null
     */
    public static String buildValidationErrorsResponse(final Set<ConstraintViolation> validationErrors) throws NullPointerException {
        if (!validationErrors.isEmpty()) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("Validation errors were found:");
        for (final ConstraintViolation violation : validationErrors) {
            builder.append(" ");
            builder.append(violation.getPropertyPath().toString());
            builder.append(" ");
            builder.append(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        }
        return builder.toString();
    }
}
