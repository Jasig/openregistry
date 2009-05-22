package org.openregistry.core.service;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;
import org.openregistry.integration.IdentifierChangeEventNotification;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Default implementation of <code>IdentifierChangeService</code>
 * @since 1.0
 */
@Service
public class DefaultIdentifierChangeService implements IdentifierChangeService {

    @Autowired
    private IdentifierChangeEventNotification idChangeNotification;

    public boolean change(IdentifierType internalIdType, Identifier internalId, IdentifierType changedIdType, Identifier changedId) {
        //TODO: Implement the change logic
        this.idChangeNotification.createAndSendFor(internalIdType.getName(), internalId.getValue(), changedIdType.getName(), changedId.getValue());

        return true;
    }
}
