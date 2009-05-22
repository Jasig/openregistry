package org.openregistry.integration;

/**
 * TODO: DOCUMENT ME!
 * @since 1.0
 */
public interface IdentifierChangeEventSender {

    void asyncSend(String eventMessage);
}
