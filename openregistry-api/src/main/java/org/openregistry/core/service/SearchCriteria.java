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

import org.openregistry.core.domain.IdentifierType;

import java.util.Date;
import java.io.Serializable;

/**
 * Criteria used to conduct a search for a set of people.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SearchCriteria extends Serializable {

    /**
     * The first or given name of the person.
     *
     * @return the given or first name of the person, or NULL.
     */
    String getGivenName();

    /**
     * The family or last name of the person.
     *
     * @return the family or last name of the person, or NULL.
     */
    String getFamilyName();

    /**
     * Returns the name as provided, *only* if it was provided as one String.
     *
     * @return the provided name.  CAN be null.
     */
    String getName();

    /**
     * The complete date of birth.  Partial date of birth searches will not work.
     * @return the date of birth, or null.
     */
    Date getDateOfBirth();

    /**
     * The value of the identifier type.  If the value is set, the type MUST be set.
     * @return the value, or null.
     */
    String getIdentifierValue();

    /**
     * The type of search identifier
     *
     */
      public IdentifierType getIdentifierType() ;
}
