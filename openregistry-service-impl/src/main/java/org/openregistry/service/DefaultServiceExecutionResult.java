package org.openregistry.service;

import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;

import java.util.List;
import java.util.Date;
import java.util.Collections;

/**
 * Implementation of a <code>ServiceExecutionResult </code>
 *
 * @since 1.0
 * @see org.openregistry.core.service.ServiceExecutionResult
 *
 */
public class DefaultServiceExecutionResult implements ServiceExecutionResult {

    private final String serviceName;

    private final Date serviceExecutionDate = new Date();

    private final Object targetObject;

    private final List<ValidationError> validationErrors;

    public DefaultServiceExecutionResult(final String serviceName, final Object targetObject) {
        this(serviceName, targetObject, null);
    }

    public DefaultServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors) {
        this.serviceName = serviceName;
        this.targetObject = targetObject;
        this.validationErrors = validationErrors != null ? Collections.unmodifiableList(validationErrors) : Collections.<ValidationError>emptyList(); 
    }


    public Date getExecutionDate() {
        return new Date(this.serviceExecutionDate.getTime());
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }
}
