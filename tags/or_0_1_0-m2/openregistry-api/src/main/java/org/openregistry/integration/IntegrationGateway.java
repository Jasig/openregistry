/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    /**
     * Take the identity data and put into the 'integration processing pipeline' (asynchronously) for the 3rd party systems to consume.
     * The typical implementation could use an ESB endpoint destination with a predefined message flow.
     * </p>
     * <p>Note: implementers should assume the asynchronous invocation semantics i.e. it should return immediately to the caller.
     *
     * @param destinationId an identifier for the destination - could be an ESB endpoint URI for example
     * @param messageBody   the body of the message to be send
     * @throws IntegrationProcessingException in case the integration processing failure occurs
     */
    void dispatch(String destinationId, Object messageBody) throws IntegrationProcessingException;

    /**
     * Put the identity data into the 'integration processing pipeline' (asynchronously) for the 3rd party systems to consume.
     * The typical implementation could use an ESB endpoint destination with a predifined message flow.
     * </p>
     * <p>Note: implementors should assume the asynchronous invocation semantics i.e. it should return immediately to the caller.
     *
     * @param destinationId an identifier for the destination - could be an ESB endpoint URI for example
     * @param messageBody   the body of the message to be send
     * @param metadata      any metadata associated with the message body
     * @throws IntegrationProcessingException in case the integration processing failure occurs
     */
    void dispatch(String destinationId, Object messageBody, Map<String, Object> metadata) throws IntegrationProcessingException;

    /**
     * Put the identity data into the 'integration processing pipeline' (synchronously) for the 3rd party systems to consume.
     * The typical implementation could use an ESB endpoint destination with a predefined message flow.
     * </p>
     * <p>Note: implementors should assume the synchronous invocation semantics i.e. it should block until the response is returned to the caller.
     *
     * @param destinationId an identifier for the destination - could be an ESB endpoint URI, web service URL, etc.
     * @param messageBody   the body of the message to be send
     * @param requiredType      the type of the response message
     * @throws IntegrationProcessingException in case the integration processing failure occurs
     * @throws IllegalArgumentException if the 'requiredType' argument does not denote the response message type
     */
    <T> T sendMessageAndReceiveResponse(String destinationId, Object messageBody, Class<T> requiredType) throws IntegrationProcessingException,
            IllegalArgumentException;


    /**
     * Put the identity data into the 'integration processing pipeline' (synchronously) for the 3rd party systems to consume.
     * The typical implementation could use an ESB endpoint destination with a predefined message flow.
     * </p>
     * <p>Note: implementors should assume the synchronous invocation semantics i.e. it should block until the response is returned to the caller.
     *
     * @param destinationId an identifier for the destination - could be an ESB endpoint URI, web service URL, etc.
     * @param messageBody   the body of the message to be send
     * @param metadata      any metadata associated with the message body
     * @param requiredType      the type of the response message
     * @throws IntegrationProcessingException in case the integration processing failure occurs
     * @throws IllegalArgumentException if the 'requiredType' argument does not denote the response message type
     */
    <T> T sendMessageAndReceiveResponse(String destinationId, Object messageBody, Map<String, Object> metadata, Class<T> requiredType)
            throws IntegrationProcessingException, IllegalArgumentException;
}
