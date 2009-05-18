package org.openregistry.core.service.reconciliation;

import java.util.List;

import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationResult;

public class ReconciliationResultImpl implements ReconciliationResult {
	
	ReconciliationType type;
	List<PersonMatch> matches;
	
	public ReconciliationResultImpl(final ReconciliationType type, final List<PersonMatch> matches) {
		this.type = type;
		this.matches = matches;
	}

	public List<PersonMatch> getMatches() {
		return this.matches;
	}

	public ReconciliationType getReconciliationType() {
		return this.type;
	}

    public boolean noPeopleFound() {
        return (this.type == ReconciliationResult.ReconciliationType.NONE);
    }

    public boolean personAlreadyExists() {
        return (this.type == ReconciliationResult.ReconciliationType.EXACT);
    }

    public boolean multiplePeopleFound() {
        return (this.type == ReconciliationResult.ReconciliationType.MAYBE);
    }
}
