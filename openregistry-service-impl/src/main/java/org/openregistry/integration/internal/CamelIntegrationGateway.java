package org.openregistry.integration.internal;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ExchangePattern;
import org.openregistry.integration.IntegrationGateway;
import org.openregistry.integration.IntegrationProcessingException;

import java.util.Map;

/**
 * <code>IntegrationGateway</code> implementation based on Apache Camel integration framework.
 * <p>This implementation wraps Camel's <code>ProducerTemplate</code> API.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Component
@Scope("singleton")
public class CamelIntegrationGateway implements IntegrationGateway {

    @Autowired
    private ProducerTemplate camelTemplate;

    public void dispatch(String destinationId, Object messageBody) throws IntegrationProcessingException {
        try {
            this.camelTemplate.asyncSendBody(destinationId, messageBody);
        }
        catch (Throwable e) {
            throw new IntegrationProcessingException(e);
        }
    }

    public void dispatch(String destinationId, Object messageBody, Map<String, Object> metadata) throws IntegrationProcessingException {
        try {
            this.camelTemplate.asyncRequestBodyAndHeaders(destinationId, messageBody, metadata);
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
        catch(ClassCastException ex) {
            throw new IllegalArgumentException("The response message body is of incompatible type with passed in argument");
        }
        catch (Throwable e) {
            throw new IntegrationProcessingException(e);
        }

    }

    public <T> T sendMessageAndReceiveResponse(String destinationId, Object messageBody, Map<String, Object> metadata, Class<T> requiredType)
            throws IntegrationProcessingException, IllegalArgumentException {

        try {
            return this.camelTemplate.requestBodyAndHeaders(destinationId, messageBody, metadata, requiredType);
        }
        catch(ClassCastException ex) {
            throw new IllegalArgumentException("The response message body is of incompatible type with passed in argument");
        }
        catch (Throwable e) {
            throw new IntegrationProcessingException(e);
        }
    }
}
