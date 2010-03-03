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
package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.Type;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;

/**
 * An Sor Person is the representation of the person as the System of Record knows the person.   This is in isolation
 * to the other systems of record.  The {@link org.openregistry.core.domain.Person} interface represents the final
 * calculation of the person from all the SoRs.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SorPerson {

    /**
     * Identifier as assigned from the System of Record
     *
     * @return the identifier from the system of record.  CANNOT be null.
     */
    String getSorId();

    /**
     * Identifier for Sor Person
     * @return the identifier for the sor person record.
     */
    Long getId();

    /**
     * Sets the Identifier for this Person WITHIN the system of record.  At Rutgers, this would be an RUID.
     * @param id the identifier. CANNOT be null, if setting.
     */
    void setSorId(String id);

    /**
     * The identifier for the System of Record that is asserting this person.  CANNOT be null.
     *
     * @return the identifier for the System of Record
     */
    String getSourceSor();

    /**
     * Sets the identifier for the system of record.
     *
     * @param sorIdentifier the system of record identifier.
     */
    void setSourceSor(String sorIdentifier);

    /**
     * Returns the set of names the System of Record knows about the Person.
     * <p>
     * There MUST be at least ONE name returned.
     * @return the names, minimum of one.  CANNOT be null.
     */
    List<Name> getNames();

    /**
     * The official date of birth that this System of Record is aware of.
     * @return the date of birth that this system is aware of.  CAN be null.
     */
    Date getDateOfBirth();

    /**
     * Sets the date of birth for this person, as known by the System of Record.
     *
     * @param date the date of birth.
     */
    void setDateOfBirth(Date date);

    /**
     * Retrieves the gender of the person, as known by the System of Record.  CAN be NULL.
     * @return the gender.
     */
    String getGender();

    /**
     * Sets the gender of the person, as known by the System of Record.
     * @param gender the gender.
     */
    void setGender(String gender);

    /**
     * Adds a name with the correct type to the set of names.
     *
     * @return the name that was added.
     */
    Name addName();

    /**
     * Adds a name to the set of names.
     */
    void addName(Name name);
    
    /**
     * Adds a name with the given type to the set of names.
     *
     * @return the name that was added.
     */
    Name addName(Type type);

    /**
     * Finds an SoR Name (and returns it) based on the Name Id provided.
     *
     * @param id the id of the name.
     * @return the SorName, if found, otherwise, null.
     */
    Name findNameByNameId(final Long id);

    /**
     * The SSN of the person.   Can be null.
     * @return the SSN of the person.
     */
    String getSsn();

    /**
     * Sets the SSN of the person.
     * 
     * @param ssn the SSN of the person.
     */
    void setSsn(String ssn);

    /**
     * Adds an SoR Role (and returns it) based on the roleInfo.
     *
     * @param roleInfo the type of role to add.
     * @return the newly created SorRole.
     */
	SorRole addRole(RoleInfo roleInfo);

    /**
     * Retrieves the identifier of the Calculated Person associated with this System of Record person.  Portions of the
     * System of Record Person were used to calculate this Calculated Person.
     *
     * @return the id, or null, if they have not been linked yet.
     */
    Long getPersonId();

    /**
     * Sets the identifier of the Calculated Person that this System of Record person is associated with.
     *
     * @param personId the identifier for the Calculated Person.
     */
    void setPersonId(Long personId);

    /**
     * Retrieves the list of System of Record roles.
     * @return the list of roles, CANNOT be null.  CAN be empty.
     */
    List<SorRole> getRoles();

    /**
     * Find the Sor Role for a given RoleInfo code.
     * @param code  Role Info Code.
     * @return
     */
    SorRole pickOutRole(String code);

    /**
     * Finds the SorRole by the SoR-assigned ID (or the one we assigned to it).
     * This is NOT the internal ID.
     *
     * @param sorRoleId the ID to find.
     * @return the role, if found.
     */
    SorRole findSorRoleBySorRoleId(String sorRoleId);

    /**
     * Returns the SoR Role that matches the internal id.
     *
     * @param roleId the internal role id.  CANNOT be NULL.
     * @return the SoR role if one exists.  Otherwise, NULL.
     */
    SorRole findSorRoleById(Long roleId);

    /**
     * Retrieves a collection of K->V un-typed attributes that SoR might pass along
     *
     * <p> SoRs and the target OR installation should agree on the common attributes and their keys,
     * for example in local batch jobs configurations, etc.
     *
     * @return Map representing K->V attributes that a target SoR wishes to expose
     */
    Map<String, String> getSorLocalAttributes();
    
    void setSorLocalAttributes(Map<String, String> attributes);

}
