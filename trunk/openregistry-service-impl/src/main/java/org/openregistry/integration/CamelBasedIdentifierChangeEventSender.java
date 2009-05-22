package org.openregistry.integration;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.EndpointInject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Implenetation of <code>IdentifierChangeEventSender</code> based on Apache Camel integration broker framework
 * @since 1.0
 */
public class CamelBasedIdentifierChangeEventSender implements IdentifierChangeEventSender {

    private ProducerTemplate camelTemplate;

    private String identifierChangeEventDestinationUri;

    public void setCamelTemplate(ProducerTemplate camelTemplate) {
        this.camelTemplate = camelTemplate;
    }

    public void setIdentifierChangeEventDestinationUri(String identifierChangeEventDestinationUri) {
        this.identifierChangeEventDestinationUri = identifierChangeEventDestinationUri;
    }

    public void asyncSend(String eventMessage) {
        this.camelTemplate.asyncSendBody(this.identifierChangeEventDestinationUri, eventMessage);
    }
}
