package org.openregistry.core.service;

import java.io.Serializable;

/**
 * Deletion Criteria.  This tells the service what to do with the person we've given it when we tell it to delete it.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DeletionCriteria extends Serializable {

    /**
     * Answers the question of whether we should remove the entire person or not.
     *
     * @return true if we should, false otherwise.
     */
    boolean isEntirePerson();

    /**
     * Sets whether we should delete the entire person or not.
     *
     * @param entirePerson true if we should, false otherwise.
     */
    void setEntirePerson(boolean entirePerson);
}

