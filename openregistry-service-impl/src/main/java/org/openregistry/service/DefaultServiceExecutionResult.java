package org.openregistry.service;

import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;

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

    private final ReconciliationResult reconciliationResult;

    private final boolean succeeded;

    public DefaultServiceExecutionResult(final String serviceName, final Object targetObject) {
        this(serviceName, targetObject, null, null);
    }

    public DefaultServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors) {
        this(serviceName, targetObject, validationErrors, null);
    }

    public DefaultServiceExecutionResult(final String serviceName, final Object targetObject, final ReconciliationResult reconciliationResult) {
        this(serviceName, targetObject, null, reconciliationResult);
    }

    protected DefaultServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors, final ReconciliationResult reconciliationResult) {
        this.serviceName = serviceName;
        this.targetObject = targetObject;
        this.validationErrors = validationErrors != null ? Collections.unmodifiableList(validationErrors) : Collections.<ValidationError>emptyList();
        this.reconciliationResult = reconciliationResult;
        this.succeeded = this.validationErrors.isEmpty() && reconciliationResult == null;
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

    public boolean succeeded() {
        return succeeded;
    }

    public ReconciliationResult getReconciliationResult() {
        return this.reconciliationResult;
    }
}
