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

import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;

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

    ReconciliationResult reconciliationCheck(SorPerson reconciliationCriteria);

}
