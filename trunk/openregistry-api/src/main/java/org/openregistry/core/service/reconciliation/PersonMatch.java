package org.openregistry.core.service.reconciliation;

import org.openregistry.core.domain.Person;

import java.util.List;

/**
 * The reconciliation match for a person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PersonMatch {

    List<FieldMatch> getMatches();

    /**
     * Valid range from 0 - 100.
     *
     * @return
     */
    int getConfidenceLevel();

    Person getPerson();
}
