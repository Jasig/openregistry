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

package org.openregistry.core.domain;

import java.util.*;
import java.io.Serializable;

import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorRole;

/**
 * Entity representing canonical persons and their identity in the Open Registry system.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Person extends Serializable {

    /**
     * Unique identifier for this person.
     *
     * @return the unique identifier for this person.
     */
    Long getId();

    /**
     * Returns the set of names the System of Record knows about the Person.
     * <p/>
     * There MUST be at least ONE name returned.
     *
     * @return the names, minimum of one.  CANNOT be null.
     */
    Set<? extends Name> getNames();

    /**
     * Adds a new name based off the SorName.
     * @param sorName the name to add.   CANNOT be NULL.
     */
    void addName(SorName sorName);

    /**
     * Get a collection of roles associated with this person
     *
     * @return a collection of roles the person currently has or an empty collection.
     *         Note to implementers: this method should never return null.
     */
    List<Role> getRoles();

    /**
     * Add a new role to a collection of roles this person holds and populate it with the data from the given sorRole.
     *
     * @param sorRole the sor role to populate the data from
     * @return role new role to add to this person instance.  NEVER returns null.
     */
    Role addRole(SorRole sorRole);

    /**
     * Get identifiers associated with this person.
     *
     * @return PersonIdentifiers. Please note that this method should never return null.
     */
    Set<? extends Identifier> getIdentifiers();

    /**
     * Get the name of the person.
     *
     * @return the name of the person, never null.
     */
    Name getPreferredName();

    Name getOfficialName();

    /**
     * The gender of the person.  Current restrictions include only M or F.
     *
     * @return the gender, never null.
     */
    String getGender();

    /**
     * The date of birth, never null.
     *
     * @return the date of birth.
     */
    Date getDateOfBirth();

    /**
     * The date of birth, never null.
     *
     * @param dateOfBirth the date of birth.
     */
    void setDateOfBirth(Date dateOfBirth);

    void setGender(String gender);

    /**
     * Constructs a new Identifier based on the provided type and value.
     *
     * @param identifierType the identifier type
     * @param value the value
     * @return the new identifier.
     */
    Identifier addIdentifier(IdentifierType identifierType, String value);

    /**
     * Pick out the specific <code>Role</code> identified by provided role code
     * from the collection of this person's roles
     *
     * @return Role of this person for the provided identifier or a null if this person does not have such a role
     */
    Role pickOutRole(String code);

    /**
     * Finds the matching SoR Role Id for this calculated role.
     *
     * @param sorRoleId the internal role id to check for.  NOTE, this is NOT the SoR-assigned Role Id (even if the
     * method is poorly named for now).
     * @return the role, if found.  Null otherwise.
     */
    Role findRoleBySoRRoleId(Long sorRoleId);

    /**
     * Returns a Map of the primary identifiers, in a key/value pair with type.
     *
     * @return the map, keyed on Identifier Type of primary identifiers.  CANNOT be NULL. CAN be EMPTY.
     */
    Map<String,Identifier> getPrimaryIdentifiersByType();

    /**
     * Returns a list of identifiers grouped by type.  The 
     * @return the map of identifiers, with each list sorted by primary, then non-primary.  CANNOT be NULL. CAN be EMPTY.
     */
    Map<String, Deque<Identifier>> getIdentifiersByType();

    /**
     * Generates a new Activation Key.  If there are any current activation keys, they
     * are removed and replaced with this new one.
     *
     * @param start the start date for the activation key
     * @param end the end date for the activation key
     * @return the new activation key that was generated.  CANNOT be NULL.
     */
    ActivationKey generateNewActivationKey(Date start, Date end);

    /**
     * Generates a new activation key.  If there are any current activation keys, they
     * are removed and replaced with this new one.
     * <p>
     * Note: as you don't specify a start date here, the CURRENT date/time is assumed
     * to be the start date.
     * </p>
     * @param end the end date for the activation key
     * @return the new activation key that was generated.  CANNOT be NULL.
     */
    ActivationKey generateNewActivationKey(Date end);

    /**
     * Returns any currently associated activation keys (even if expired).
     *
     * @return the currently associated activation key.  CAN be NULL.
     */
    ActivationKey getCurrentActivationKey();

    /**
     * Removes any current activation key, regardless of whether its expired, or not yet valid.
     */
    void removeCurrentActivationKey();

}
