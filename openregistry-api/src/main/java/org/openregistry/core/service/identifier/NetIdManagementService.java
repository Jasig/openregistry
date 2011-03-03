package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.service.ServiceExecutionResult;

/**
 * @since 1.0
 */
public interface NetIdManagementService {

    /**
     * Change the net id of the current <code>Person</code>
     *
     * @param oldNetIdValue
     * @param newNetIdValue
     * @param primary       is this a privare net id
     * @return The newly changed <code>Identifier</code> instance
     */
    ServiceExecutionResult<Identifier> changeNetId(String oldNetIdValue, String newNetIdValue, boolean primary) throws IllegalArgumentException, IllegalStateException;
}
