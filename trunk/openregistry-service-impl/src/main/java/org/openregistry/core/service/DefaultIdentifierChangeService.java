package org.openregistry.core.service;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;
import org.springframework.stereotype.Service;
import org.apache.camel.ProducerTemplate;

/**
 * Default implementation of <code>IdentifierChangeService</code>
 * @since 1.0
 */
@Service
public class DefaultIdentifierChangeService implements IdentifierChangeService {

    public boolean change(IdentifierType internalIdType, Identifier internalId, IdentifierType changedIdType, Identifier changedId) {
        //TODO: Implement the change logic
        return true;
    }
}
