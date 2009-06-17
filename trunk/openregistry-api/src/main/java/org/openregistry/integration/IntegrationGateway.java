package org.openregistry.integration;

import java.util.Map;

/**
 * An abstraction representing an integration subsystem for Open Identity Registry.
 * The role of <code>IntegrationGateway</code> is to de-couple the core parts of OIR from 3rd party systems and abstract away the
 * mechanism of actual integration and make it pluggable. For example different ESB-based implementations could be provided,
 * configured and plugged into the core OIR system or web services-based one could be used, etc.
 * <p/>
 * <p><strong>Concurrent semantics:</strong> implementations of this interface must be thread-safe
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public interface IntegrationGateway {

    void dispatch(String destinationId, Object body) throws IntegrationProcessingException;

    void dispatch(String destinationId, Object body, Map<String, Object> metadata) throws IntegrationProcessingException;

    <T> T sendAndReceiveResponse(String destinationId, Object body, Class<T> requiredType) throws IntegrationProcessingException;

    <T> T sendAndReceiveResponse(String destinationId, Object body, Map<String, Object> metadata, Class<T> requiredType) throws IntegrationProcessingException;
}
