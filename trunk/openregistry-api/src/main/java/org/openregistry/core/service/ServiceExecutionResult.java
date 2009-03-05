package org.openregistry.core.service;

import java.util.Date;

/**
 * A container encapsulating results of any number of different Open Registry public service API invocations.
 * Such results are validation errors if any, time stamp of the service invocation, service name being executed.
 *
 * @since 1.0
 */
public interface ServiceExecutionResult {

    // TODO we need to put in Dmitriy's refactoring and not depend on Spring --sb

    /**
     * Get an instant in time when a particular service, represented by this result, has been executed
     * @return Date representing a service execution date and time
     */
    Date getExecutionDate();

    /**
     * Get a symbolic name for an executed service represented by this result
     * @return a string token representing a symbolic name of a service that has been executed
     */
    String getServiceName();
}
