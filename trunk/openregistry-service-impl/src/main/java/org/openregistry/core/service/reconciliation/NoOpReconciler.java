package org.openregistry.core.service.reconciliation;

import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.domain.sor.ReconciliationCriteria;

import java.util.List;
import java.util.Collections;

/**
 * This should get replaced with something that actually does something.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class NoOpReconciler implements Reconciler {

    public ReconciliationResult reconcile(final ReconciliationCriteria reconciliationCriteria) {
        return new ReconciliationResult() {
            public ReconciliationType getReconciliationType() {
                return ReconciliationType.NONE;
            }

            public List<PersonMatch> getMatches() {
                return Collections.EMPTY_LIST;
            }

            public boolean noPeopleFound() {
                return true;
            }

            public boolean personAlreadyExists() {
                return false;
            }

            public boolean multiplePeopleFound() {
                return false;  
            }
        };
    }
}
