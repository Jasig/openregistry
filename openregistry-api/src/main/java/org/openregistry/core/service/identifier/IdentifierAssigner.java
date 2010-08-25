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

package org.openregistry.core.service.identifier;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * Assigns an identifier to a person based the data provided from the system of record.
 * It's the job of the identifier assigner to determine if it needs to add an identifier for a person or not.
 * 
 * IdentifierAssigners should be able to be run multiple times without creating duplicate identifiers.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierAssigner {

    /**
     * Adds the identifier to the particular person.  This is not necessarily saved until an explicit save call
     * is made so identifier creation systems should not rely on the identifier table in OR to check whether a 
     * value was used or not.
     * 
     * This method should not generate more than one primary, non-deleted identifier.
     *
     * @param sorPerson the original SoR person.
     * @param person the person to add the identifier to.
     */
    void addIdentifierTo(SorPerson sorPerson, Person person);

    /**
     * Returns the type of identifier that this assigner can assign.
     * @return identifier type
     */
    String getIdentifierType();
}
