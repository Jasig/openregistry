package org.openregistry.core.service;

import org.openregistry.core.domain.Identifier;

/**
 * Component defining the main public API for interacting with Open Registry Activation subsystem.
 * It acts as the main organizing application service layer component
 * for the Activation subsystem of the Open Registry.
 *
 * @since 1.0
 */
public interface ActivationService {

    /**
     * Verify the activationKey.
     *
     * @param identifierType
     * @param identifierValue
     * @param activationKey
     * @return Result of verificaiton.  Validation errors if they occurred.
     */
    ServiceExecutionResult verifyActivationKey(final String identifierType, final String identifierValue, final String activationKey);

    /**
     * Activate the NetID.
     *
     * @param identifierType
     * @param identifier
     * @param password
     * @return Result of activation.  Validation errors if they occurred.
     */
    ServiceExecutionResult activateNetID(final String identifierType, final Identifier identifier, String password);

}