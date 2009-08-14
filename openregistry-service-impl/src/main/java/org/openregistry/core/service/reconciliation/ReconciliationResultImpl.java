/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
