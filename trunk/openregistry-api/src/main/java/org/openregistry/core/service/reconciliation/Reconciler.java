package org.openregistry.core.service.reconciliation;

import org.openregistry.core.domain.sor.SorPerson;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Mar 11, 2009
 * Time: 2:59:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Reconciler {

    ReconciliationResult reconcile(SorPerson person);

}
