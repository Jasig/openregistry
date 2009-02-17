package org.openregistry.service;

import org.springframework.validation.Errors;
import org.openregistry.core.service.ServiceExecutionResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;

/**
 * Immutable implementation of a <code>ServiceExecutionResult </code>
 *
 * @since 1.0
 * @see org.openregistry.core.service.ServiceExecutionResult
 */
public class DefaultServiceExecutionResult implements ServiceExecutionResult {

    private final String serviceName;

    private final Date serviceExecutionDate;

    private final Errors errors;

    public DefaultServiceExecutionResult(String serviceName, Date serviceExecutionDate) {
        this(serviceName, serviceExecutionDate, null);
    }

    public DefaultServiceExecutionResult(String serviceName, Date serviceExecutionDate, Errors errors) {
        this.serviceName = serviceName;
        this.serviceExecutionDate = serviceExecutionDate;
        this.errors = errors;
    }

    public Errors getErrors() {
        return this.errors;
    }

    public Date getExecutionDate() {
        return new Date(this.serviceExecutionDate.getTime());
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
