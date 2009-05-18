package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;

import java.util.List;

/**
 * Implementation of a <code>ServiceExecutionResult </code>
 *
 * @since 1.0
 * @see org.openregistry.core.service.ServiceExecutionResult
 *
 */
public class ReconciliationServiceExecutionResult extends GeneralServiceExecutionResult {

    private final ReconciliationResult reconciliationResult;

    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject) {
        this(serviceName, targetObject, null, null);
    }

    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors) {
        this(serviceName, targetObject, validationErrors, null);
    }
    
    public ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject, final ReconciliationResult reconciliationResult) {
        this(serviceName, targetObject, null, reconciliationResult);
    }

    protected ReconciliationServiceExecutionResult(final String serviceName, final Object targetObject,final List<ValidationError> validationErrors, final ReconciliationResult reconciliationResult) {
        super(serviceName, targetObject, validationErrors);
        this.reconciliationResult = reconciliationResult;
        setSucceeded(getValidationErrors().isEmpty() && targetObject instanceof Person);
    }

    public ReconciliationResult getReconciliationResult() {
        return this.reconciliationResult;
    }

}
