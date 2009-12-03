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
package org.jasig.openregistry.test.service;

import org.jasig.openregistry.test.domain.MockPerson;
import org.openregistry.core.service.reconciliation.FieldMatch;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.ReconciliationResult.*;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.Person;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author Nancy Mond
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class MockReconciler implements Reconciler {

    private ReconciliationType desiredResult;

    public MockReconciler() {

    }

    public MockReconciler(final ReconciliationType result) {
        if (result == null) {
            throw new IllegalArgumentException("result cannot be null.");
        }
        this.desiredResult = result;
    }

    public void setReconciliationType(final ReconciliationType reconciliationType) {
        this.desiredResult = reconciliationType;
    }

    public ReconciliationResult reconcile(final ReconciliationCriteria reconciliationCriteria) {
        return new ReconciliationResult() {

            public ReconciliationType getReconciliationType() {
                return desiredResult;
            }

            public List<PersonMatch> getMatches() {
                List<PersonMatch> exactMatch = new ArrayList<PersonMatch>();
                List<PersonMatch> severalMatch = new ArrayList<PersonMatch>();
                PersonMatch personMatch = new PersonMatch(){
                    public List<FieldMatch> getFieldMatches(){
                        return Collections.emptyList();
                    }
                    public int getConfidenceLevel() { return 0; }
                    public Person getPerson() { return new MockPerson();}
                	public int compareTo(final PersonMatch personMatch) {
                		final int level = getConfidenceLevel() - personMatch.getConfidenceLevel();
                		if (level == 0) {
                			final int result = getPerson().getOfficialName().getFamily().compareTo(personMatch.getPerson().getOfficialName().getFamily());
                			return result == 0 ? getPerson().getOfficialName().getGiven().compareTo(personMatch.getPerson().getOfficialName().getGiven()): result;
                		}
                		return level;
                	}
                    };
                exactMatch.add(personMatch);
                severalMatch.add(personMatch);
                severalMatch.add(personMatch);

                if (desiredResult.equals(ReconciliationType.EXACT)) return exactMatch;
                else if (desiredResult.equals(ReconciliationType.MAYBE)) return severalMatch;
                return Collections.emptyList();
            }

            public boolean noPeopleFound() {
                return desiredResult.equals(ReconciliationType.NONE);
            }

            public boolean personAlreadyExists() {
                return desiredResult.equals(ReconciliationType.EXACT);
            }

            public boolean multiplePeopleFound() {
                return desiredResult.equals(ReconciliationType.MAYBE);
            }
        };
    }

    public boolean reconcilesToSamePerson(final SorPerson sorPerson) {
        return true;
    }
}