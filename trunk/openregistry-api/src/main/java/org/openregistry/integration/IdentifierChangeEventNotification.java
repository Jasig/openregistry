package org.openregistry.integration;

/**
 * TODO: DOCUMENT ME!
 *
 * @since 1.0
 */
public interface IdentifierChangeEventNotification {

    void createAndSendFor(String internalIdentifierType, String internalIdentifierValue, String changedIdentifierType, String changedIdentifierValue);
}
