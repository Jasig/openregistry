package org.openregistry.service.reconciliation;

import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.domain.sor.SorPerson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

/**
 * This should get replaced with something that actually does something.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component
public final class NoOpReconciler implements Reconciler {

    public ReconciliationResult reconcile(final SorPerson person) {
        return new ReconciliationResult() {
            public ReconciliationType getReconciliationType() {
                return ReconciliationType.NONE;
            }

            public List<PersonMatch> getMatches() {
                return Collections.emptyList();
            }
        };
    }
}
