/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.service.reconciliation;

import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;

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

            public void setReconciliationFeedback(String feedback){
            }

            public String getReconciliationFeedback(){
                return "feedback";
            }
        };
    }

    public boolean reconcilesToSamePerson(final SorPerson sorPerson){
       return true;
    }
}
