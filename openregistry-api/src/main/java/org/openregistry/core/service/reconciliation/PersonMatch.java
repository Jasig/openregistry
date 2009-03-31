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

    List<FieldMatch> getMatches();

    /**
     * Valid range from 0 - 100.
     *
     * @return level
     */
    int getConfidenceLevel();

    Person getPerson();
}
