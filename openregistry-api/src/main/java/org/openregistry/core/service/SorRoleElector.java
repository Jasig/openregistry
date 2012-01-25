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
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;

import java.util.List;

/**
 * Elects a sor role in case more than one sor is trying to add the same role
 */
public interface SorRoleElector {
    /**
     * Based on predefined configuration[implementation specific] this method decide if the new incoming sor can be added to the calculated person
     * @param newSorRole a new sor role that is candidate to be converted to calculatedRole
     * @param person Calculated person target of the newSorRole addition
     * @return
     */
      public boolean addSorRole(SorRole newSorRole, Person person);

    /**
     * this method remove a calculated role from calculated person
     * Implementation of this method should also try to find another sor role that
     * would take place of deleted role
     * @param person
     * @param roleToDelete
     * @param sorPersons
     * @return
     */
      public boolean removeCalculatedRole(Person person,Role roleToDelete,List<SorPerson> sorPersons);

}
