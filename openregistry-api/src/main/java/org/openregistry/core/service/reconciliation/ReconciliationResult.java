package org.openregistry.core.service.reconciliation;

import java.util.List;
import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationResult extends Serializable {

    enum ReconciliationType {EXACT, NONE, MAYBE}

    ReconciliationType getReconciliationType();

    List<PersonMatch> getMatches();
}
