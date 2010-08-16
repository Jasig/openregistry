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

package org.openregistry.core.service;

import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Chooses which value of a field should be persisted.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public interface FieldElector<T> {

    /**
     * Elects the value to store for this particular field (i.e. Date).
     *
     * @param newestPerson the latest addition to the list of SoR People.  CAN be NULL if this was a DELETE.
     * @param sorPersons the SoR Persons to check. CAN be EMPTY but NOT NULL.
     * @param deletion flags whether this election was called because someone was deleted.  This means that the
     * newestPerson is the last person deleted.
     * @return the value that's been elected.  CAN be NULL.
     */
    T elect(SorPerson newestPerson, List<SorPerson> sorPersons, boolean deletion);
}
