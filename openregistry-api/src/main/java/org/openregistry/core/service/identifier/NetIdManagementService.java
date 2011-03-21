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
     * @param currentNetIdValue
     * @param newNetIdValue
     * @return The newly changed <code>Identifier</code> instance
     */
    ServiceExecutionResult<Identifier> changePrimaryNetId(String currentNetIdValue, String newNetIdValue) throws IllegalArgumentException, IllegalStateException;

    /**
     * Change the net id of the current <code>Person</code>
     *
     * @param primaryNetIdValue
     * @param newNonPrimaryNetIdValue
     * @return The newly changed <code>Identifier</code> instance
     */
    ServiceExecutionResult<Identifier> addNonPrimaryNetId(String primaryNetIdValue, String newNonPrimaryNetIdValue) throws IllegalArgumentException, IllegalStateException;
}
