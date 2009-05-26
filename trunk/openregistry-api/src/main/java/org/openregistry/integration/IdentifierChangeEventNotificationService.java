package org.openregistry.integration;

/**
 * A component responsible for firing Open Registry messages related to an identifier change events.
 *
 * Typical implementations could send such event messages to a variety of destinations.
 *
 * @author Dmitriy kopylenko
 * @since 1.0
 */
public interface IdentifierChangeEventNotificationService {
 
    /**
     * Create an event message and send it to a subsystem(s) that understands how to deal with such messages. The event message format
     * is implementation-dependent.
     * 
     * @param internalIdentifierType
     * @param internalIdentifierValue
     * @param changedIdentifierType
     * @param changedIdentifierValue
     */
    void createAndSendEventMessageFor(String internalIdentifierType, String internalIdentifierValue, String changedIdentifierType, String changedIdentifierValue);
}
