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

import org.openregistry.core.domain.Person;

import java.io.Serializable;
import java.util.List;

/**
 * The reconciliation match for a person.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PersonMatch extends Serializable, Comparable<PersonMatch> {

    /**
     * Returns the list of fields that matched.  This is an OPTIONAL method that returns additional information, if
     * available.  However, despite being optional, this CANNOT return NULL.
     * 
     * @return the list of fields that matched.  CANNOT return NULL. CAN return EMPTY.
     */
    List<FieldMatch> getFieldMatches();

    /**
     * Valid range from 0 - 100.
     *
     * @return level
     */
    int getConfidenceLevel();

    /**
     * Retrieves the person who matched.
     *
     * @return the person who matched.  CANNOT be null.
     */
    Person getPerson();

}
