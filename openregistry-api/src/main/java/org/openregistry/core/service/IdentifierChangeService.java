package org.openregistry.core.service;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;

/**
 * TODO: DOCUMENT ME!
 * @since 1.0
 */
public interface IdentifierChangeService {

    boolean change(IdentifierType internalIdType, Identifier internalId, IdentifierType changedIdType, Identifier changedId);                                  
}
