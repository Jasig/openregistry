package org.openregistry.integration

/**
 * Groovy-based implementation
 * @since 1.0
 */
public class DefaultIdentifierChangeEventNotification implements IdentifierChangeEventNotification {

    IdentifierChangeEventSender identifierChangeEventSender;

    public void createAndSendFor(String internalIdentifierType,
                                 String internalIdentifierValue,
                                 String changedIdentifierType,
                                 String changedIdentifierValue) {

        this.identifierChangeEventSender.asyncSend('<from-groovy>Oh yeah</from-groovy>')
    }
}