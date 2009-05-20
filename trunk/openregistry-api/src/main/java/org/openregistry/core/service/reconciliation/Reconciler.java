package org.openregistry.core.service.reconciliation;

import org.openregistry.core.domain.sor.ReconciliationCriteria;

import java.io.Serializable;

/**
 * Attempts to reconcile the supplied Person with the OpenRegistry System.
 * <p>
 * Reconciler's can implement any algorithm they'd like to attempt to locate matches.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Reconciler extends Serializable {

    /**
     * Executes the algorithm used to locate matches.
     *
     * @param reconciliationCriteria the person to attempt to match.
     * @return the result of the match attempt.  CANNOT be NULL.
     */
    ReconciliationResult reconcile(ReconciliationCriteria reconciliationCriteria);

}
