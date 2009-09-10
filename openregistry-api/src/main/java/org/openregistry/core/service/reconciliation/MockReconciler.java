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

import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.MockPerson;

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

    private final String MATCH_TYPE_NONE = "NONE";
    private final String MATCH_TYPE_EXACT = "EXACT";
    private final String MATCH_TYPE_MAYBE = "MAYBE";

    private String desiredResult = null;

    public MockReconciler(String result){
        this.desiredResult = result;
    }

    public ReconciliationResult reconcile(ReconciliationCriteria reconciliationCriteria){
        return reconcile(reconciliationCriteria, desiredResult);
    }

    public ReconciliationResult reconcile(ReconciliationCriteria reconciliationCriteria, final String matchType){

        return new ReconciliationResult() {

            public ReconciliationType getReconciliationType() {
                if (matchType.equals(MATCH_TYPE_MAYBE)) return ReconciliationType.MAYBE;
                else if (matchType.equals(MATCH_TYPE_EXACT)) return ReconciliationType.EXACT;
                else return ReconciliationType.NONE;
            }

            public List<PersonMatch> getMatches() {
                List<PersonMatch> exactMatch = new ArrayList<PersonMatch>();
                List<PersonMatch> severalMatch = new ArrayList<PersonMatch>();
                PersonMatch personMatch = new PersonMatch(){
                    public List<FieldMatch> getFieldMatches(){
                        return Collections.EMPTY_LIST;
                    }
                    public int getConfidenceLevel() { return 0; }
                    public Person getPerson() { return new MockPerson();}
                    };
                exactMatch.add(personMatch);
                severalMatch.add(personMatch);
                severalMatch.add(personMatch);

                if (matchType.equals(MATCH_TYPE_EXACT)) return exactMatch;
                else if (matchType.equals(MATCH_TYPE_MAYBE)) return severalMatch;
                return Collections.EMPTY_LIST;
            }

            public boolean noPeopleFound() {
                if (desiredResult.equals(MATCH_TYPE_NONE)) return true;
                else return false;
            }

            public boolean personAlreadyExists() {
                if (desiredResult.equals(MATCH_TYPE_EXACT)) return true;
                else return false;
            }

            public boolean multiplePeopleFound() {
                if (desiredResult.equals(MATCH_TYPE_MAYBE)) return true;
                else return false;
            }
        };
    }
}