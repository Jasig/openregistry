package org.openregistry.core.service.reconciliation;

import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.PersonSearch;

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
     * @param personSearch the person to attempt to match.
     * @return the result of the match attempt.  CANNOT be NULL.
     */
    ReconciliationResult reconcile(PersonSearch personSearch);

}
