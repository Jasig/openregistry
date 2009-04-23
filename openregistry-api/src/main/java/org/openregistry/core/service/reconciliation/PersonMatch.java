package org.openregistry.core.service.reconciliation;

import org.openregistry.core.domain.Person;

import java.io.Serializable;
import java.util.List;

/**
 * The reconciliation match for a person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PersonMatch extends Serializable {

    /**
     * Returns the list of fields that matched.  This is an OPTIONAL method that returns additional information, if
     * available.  However, despite being optional, this CANNOT return NULL.
     * 
     * @return the list of fields that matched.  CANNOT return NULL. CAN return EMPTY.
     */
    List<FieldMatch> getFieldMatches();

    /**
     * Valid range from 0 - 100.
     *
     * @return level
     */
    int getConfidenceLevel();

    /**
     * Retrieves the person who matched.
     *
     * @return the person who matched.  CANNOT be null.
     */
    Person getPerson();
}
