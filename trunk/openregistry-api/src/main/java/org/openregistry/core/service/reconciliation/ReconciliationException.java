package org.openregistry.core.service.reconciliation;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class ReconciliationException extends Exception {

    private final ReconciliationResult reconciliationResult;

    public ReconciliationException(final ReconciliationResult reconciliationResult) {
        this.reconciliationResult = reconciliationResult;
    }

    public ReconciliationResult getReconciliationResult() {
        return this.reconciliationResult;
    }
}
