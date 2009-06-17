package org.openregistry.integration;

/**
 * A RuntimeException indicating an unrecoverable fault during an integration event processing e.g.
 * sending an message to a remote messaging broker destination. This eexception wraps the original native
 * integration component exception which is then exposed to OR cleints (if needed via Throwable#getCause)
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class IntegrationProcessingException extends RuntimeException {

    public IntegrationProcessingException(Throwable throwable, Throwable nativeIntegrationException) {
        super(throwable);
    }
}
