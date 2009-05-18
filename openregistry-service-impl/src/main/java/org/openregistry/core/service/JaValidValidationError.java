package org.openregistry.core.service;

import org.openregistry.core.service.ValidationError;
import org.javalid.core.ValidationMessage;

/**
 * Converts a JaValid error to the domain specific error.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JaValidValidationError implements ValidationError {

    private final ValidationMessage validationMessage;

    public JaValidValidationError(final ValidationMessage validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getField() {
        return this.validationMessage.getPath();
    }

    public Object[] getArguments() {
        return this.validationMessage.getValues();
    }

    public String getCode() {
        return this.validationMessage.getMessage();
    }
}
