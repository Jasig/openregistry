package org.openregistry.core.service;

import org.openregistry.core.service.ServiceExecutionResult;
import org.openregistry.core.service.ValidationError;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class GeneralServiceExecutionResult implements ServiceExecutionResult {

    private final String serviceName;

    private final Date serviceExecutionDate = new Date();

    private final Object targetObject;

    private final List<ValidationError> validationErrors;

    private boolean succeeded;

    public GeneralServiceExecutionResult(final String serviceName, final Object targetObject) {
        this(serviceName, targetObject, null);
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // protected GeneralServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors, final ReconciliationResult reconciliationResult) {
    public GeneralServiceExecutionResult(final String serviceName, final Object targetObject, final List<ValidationError> validationErrors) {
        this.serviceName = serviceName;
        this.targetObject = targetObject;
        this.validationErrors = validationErrors != null ? Collections.unmodifiableList(validationErrors) : Collections.<ValidationError>emptyList();
        this.succeeded = this.validationErrors.isEmpty();
        if (this.succeeded) logger.info("Returning succeeded");
        else logger.info("Returning DID NOT succeed");
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

    protected void setSucceeded(boolean succeeded){
        this.succeeded = succeeded;
    }

    // TODO fix this
    public ReconciliationResult getReconciliationResult() {
        return null;
    }

}