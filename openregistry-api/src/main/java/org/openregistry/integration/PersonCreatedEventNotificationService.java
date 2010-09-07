package org.openregistry.integration;

import org.openregistry.core.domain.Person;

/**
 * A component responsible for triggering Open Registry notification message pertaining to a creation
 * of Open Registry calculated Person
 * <p/>
 * This event message is destined to all interested downstream consumers
 * <p/>
 * Typical implementations could send such event messages to a variety of destinations, via a variety of different means.
 *
 * @author Dmitriy kopylenko
 * @since 1.0
 */
public interface PersonCreatedEventNotificationService {


    /**
     * Create an event message and send it to a subsystem(s) that understands how to deal with such messages. Implementors might
     * transform an instance of <code>Person</code> to a message format (or multiple formats) that specific downstream systems understand.
     *
     * @param person a newly created person
     * @throws IntegrationProcessingException if anything goes wrong during message sending
     */
    void sendPersonCreatedNotificationMessageFor(Person person) throws IntegrationProcessingException;
}
