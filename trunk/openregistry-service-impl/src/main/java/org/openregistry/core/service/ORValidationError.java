package org.openregistry.core.service;

import org.openregistry.core.service.ValidationError;
import org.javalid.core.ValidationMessage;

import java.util.Collections;

/**
 * OR Validation errors.
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class ORValidationError implements ValidationError {

    private String field;
    private Object[] args;
    private String code;

    public ORValidationError(String field, Object[] args, String code) {
        this.field = field;
        this.code = code;
        this.args = args;
    }

    public String getField() {
        return this.field;
    }

    public Object[] getArguments() {
        return this.args;
    }

    public String getCode() {
        return this.code;
    }
}