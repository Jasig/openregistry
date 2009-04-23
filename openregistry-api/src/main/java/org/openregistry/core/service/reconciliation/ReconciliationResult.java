package org.openregistry.core.service.reconciliation;

import java.util.List;
import java.io.Serializable;

/**
 * The result of an attempt to determine if a person already exists in the system.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationResult extends Serializable {

    /**
     * Enumeration that determines how confident we are about the matching of this new person to an existing person in the
     * system.
     *
     * EXACT - means we are reasonably confident this is the one person.
     * NONE - means we could not find any matches
     * MAYBE - means one or more possible possible matches
     *
     */
    enum ReconciliationType {EXACT, NONE, MAYBE}

    /**
     * Returns the type of reconciliation that resulted.
     * 
     * @return the type of reconciliation.  CANNOT be NULL.
     */
    ReconciliationType getReconciliationType();

    /**
     * The list of possible matches.  CANNOT be NULL.  CAN be EMPTY (if ReconciliationType = NONE)
     *
     * @return the list of matches.
     */
    List<PersonMatch> getMatches();
}
