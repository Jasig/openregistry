/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.integration.internal;

import org.springframework.stereotype.Component;
import org.apache.camel.ProducerTemplate;
import org.openregistry.integration.IntegrationGateway;
import org.openregistry.integration.IntegrationProcessingException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * <code>IntegrationGateway</code> implementation based on Apache Camel integration framework.
 * <p>This implementation wraps Camel's <code>ProducerTemplate</code> API.
 *
 * <p>This implementation exposes wrapped Camel's <code>ProducerTemplate</code> so the clients could cast to this class and get underlying
 * native Camel component should such a need arise.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Singleton
public final class CamelIntegrationGateway implements IntegrationGateway {

    private final ProducerTemplate camelTemplate;

    @Inject
    public CamelIntegrationGateway(final ProducerTemplate producerTemplate) {
        this.camelTemplate = producerTemplate;
    }

    public void dispatch(String destinationId, Object messageBody) throws IntegrationProcessingException {
        try {
            this.camelTemplate.sendBody(destinationId, messageBody);
        }
        catch (Throwable e) {
            throw new IntegrationProcessingException(e);
        }
    }

    public void dispatch(String destinationId, Object messageBody, Map<String, Object> metadata) throws IntegrationProcessingException {
        try {
            this.camelTemplate.sendBodyAndHeaders(destinationId, messageBody, metadata);
        }
        catch (Throwable e) {
            throw new IntegrationProcessingException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T sendMessageAndReceiveResponse(String destinationId, Object messageBody, Class<T> requiredType) throws IntegrationProcessingException,
            IllegalArgumentException {

        try {
            return this.camelTemplate.requestBody(destinationId, messageBody, requiredType);
        }
        catch(final ClassCastException ex) {
            throw new IllegalArgumentException("The response message body is of incompatible type with passed in argument");
        }
        catch (final Throwable e) {
            throw new IntegrationProcessingException(e);
        }

    }

    public <T> T sendMessageAndReceiveResponse(String destinationId, Object messageBody, Map<String, Object> metadata, Class<T> requiredType)
            throws IntegrationProcessingException, IllegalArgumentException {

        try {
            return this.camelTemplate.requestBodyAndHeaders(destinationId, messageBody, metadata, requiredType);
        }
        catch(final ClassCastException ex) {
            throw new IllegalArgumentException("The response message body is of incompatible type with passed in argument");
        }
        catch (final Throwable e) {
            throw new IntegrationProcessingException(e);
        }
    }
}
