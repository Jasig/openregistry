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
 *
 * // TODO This is not immutable, despite what the Javadoc says -sb
 * // TODO This does not include the changes Dmitriy suggested for removing the dependency on Spring. -sb
 */
public class DefaultServiceExecutionResult implements ServiceExecutionResult {

    private final String serviceName;

    private final Date serviceExecutionDate = new Date();

    private final Errors errors;

    public DefaultServiceExecutionResult(final String serviceName) {
        this(serviceName, null);
    }

    public DefaultServiceExecutionResult(final String serviceName, final Errors errors) {
        this.serviceName = serviceName;
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
