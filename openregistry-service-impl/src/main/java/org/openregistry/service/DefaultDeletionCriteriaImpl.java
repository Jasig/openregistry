package org.openregistry.service;

import org.openregistry.core.service.DeletionCriteria;

/**
 * Default implementation of {@link org.openregistry.core.service.DeletionCriteria}.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class DefaultDeletionCriteriaImpl implements DeletionCriteria {

    private boolean entirePerson;

    public boolean isEntirePerson() {
        return this.entirePerson;
    }

    public void setEntirePerson(final boolean entirePerson) {
        this.entirePerson = entirePerson;
    }
}
