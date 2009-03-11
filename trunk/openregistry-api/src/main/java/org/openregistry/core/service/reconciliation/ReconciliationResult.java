package org.openregistry.core.service.reconciliation;

import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationResult {

    enum ReconciliationType {EXACT, NONE, MAYBE}

    ReconciliationType getReconciliationType();

    List<PersonMatch> getMatches();
}
