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

package org.openregistry.core.repository;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.service.*;

import java.util.*;

/**
 * Repository abstraction to deal with persistence concerns for <code>Person</code> entities
 * and their related entities
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface PersonRepository {

    /**
     * Find canonical <code>Person</code> entity in the Open Registry.
     *
     * @param id a primary key internal identifier for a person in Open Registry person repository
     * @return person found in the Open Registry's person repository or null if no person
     *         exist in this repository for a given internal id.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    Person findByInternalId(Long id) throws RepositoryAccessException;

    /**
     * Find canonical <code>SorPerson</code> entity in the Open Registry.
     *
     * @param id a primary key internal identifier for a sorPerson in Open Registry person repository
     * @return sorPerson found in the Open Registry's person repository or null if no person
     *         exist in this repository for a given internal id.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    SorPerson findSorByInternalId(Long id) throws RepositoryAccessException;

    /**
     * Finds the <code>Person</code> based on the identifier type and value.
     * @param identifierType the identifier type
     * @param identifierValue the identifier value.
     * @return the person.  CANNOT be null.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    Person findByIdentifier(String identifierType, String identifierValue) throws RepositoryAccessException;

    /**
     * Finds the <code>Person</code> based on the identifier value.  This does a partial match, starting
     * from the beginning.
     *
     * @param identifierValue the value to check
     * @return the list of people that match.  CANNOT be NULL. CAN be EMPTY.
     * @throws RepositoryAccessException
     */
    List<Person> findByUnknownIdentifier(String identifierValue) throws RepositoryAccessException;

    /**
     * Locates the System of Record person based on the source and the identifier the source asserts.
     *
     * @param sorSourceIdentifier the source
     * @param sorId the identifier the source uses.
     * @return if the person was found, false otherwise.
     */
    SorPerson findBySorIdentifierAndSource(String sorSourceIdentifier, String sorId);

    /**
     * Locates the System of Record person based on the source and the Person ID.
     *
     * @param personId the id of the calculated person
     * @param sorSourceIdentifier the source
     * @return the person, if found.  false otherwise.
     */
    SorPerson findByPersonIdAndSorIdentifier(Long personId, String sorSourceIdentifier);
    
    /**
     * Locates System of Record person based on the SSN attribute
     * 
     * @param ssn the SSN
     * @return the person, if found.  false otherwise.
     */
    SorPerson findSorBySSN(String ssn);

    /**
     * Searches for a person based on some or all of the supplied criteria.
     *
     * @param searchCriteria the search criteria.
     * @return a list of people that match.
     *
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    List<Person> searchByCriteria(SearchCriteria searchCriteria) throws RepositoryAccessException;

    /**
     * Find a list of <code>Person</code> entities from the supplied Family Name.
     *
     * @param family the Family Name to search for
     * @return List of people find in the Open Registry's person repository or an empty list if
     *         no people exist with this Family Name.
     * @throws RepositoryAccessException
     */
    List<Person> findByFamilyName(String family) throws RepositoryAccessException;

    /**
     * Persist or update an instance of a canonical <code>Person</code> entity in the Open Registry.
     *
     * @param person a person to persist or update in the person repository.
     * @return person which has been saved in the repository.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    Person savePerson(Person person) throws RepositoryAccessException;

    /**
     * Saves the System of Record person.
     *
     * @param person the person to save.
     * @return the person which was saved in the repository.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    SorPerson saveSorPerson(SorPerson person) throws RepositoryAccessException;

    /**
     * Removes the SoR Role from the database.  This method ASSUMES your code is handling the removal of the person's
     * role from the person object BEFORE calling deleteSorRole.
     *
     * @param person the person who the role is being deleted from.
     * @param role the role that is being removed.
     */
    void deleteSorRole(SorPerson person, SorRole role);

    /**
     * Returns the SoR records for a particular person.
     *
     * @param person the person
     * @return a list of sorPerson records.
     */
    List<SorPerson> getSoRRecordsForPerson(Person person);

    /**
     * Returns the SoRRoleRecord  for a particular calculated Role.
     *
     * @param calculatedRole the calculated role
     * @return a sorRole that is associated with this calculated role
     */
    SorRole getSoRRoleForRole(Role calculatedRole);
    /**
     * Returns the Calculated role  for a particular sor Role.
     *
     * @param sorRole the sor role
     * @return a a calculated role if found , null otherwise
     */
    Role getCalculatedRoleForSorRole(SorRole sorRole);

    /**
     * Returns the count of the number of SoR records for a particular person.
     *
     * @param person the person
     * @return the number of records.  Must be >=0.
     */
    Number getCountOfSoRRecordsForPerson(Person person);

    /**
     * Deletes the Sor Person from the repository.
     *
     * @param person the person to remove.  CANNOT be null.
     */
    void deleteSorPerson(SorPerson person);

    /**
     * Removes the calculated person from the database. Note, this method does NOT check whether there would be any orphaned SOR
     * records associated with this person.  If there are orphaned SOR records, it could mean that the person comes back!
     *
     * @param person the person to delete. CANNOT be null.
     */
    void deletePerson(Person person);

    /**
     * Updates the role in the database.
     *
     * @param person the person who's role is being updated.
     * @param role the role that is being updated.
     */
    void updateRole(Person person, Role role);

    /**
     * Updates the sor role in the database.
     *
     * @param role the sor role being updated.
     * @return the updated SoR role.
     */
    SorRole saveSorRole(SorRole role);

	List<Person> findByEmailAddressAndPhoneNumber(String email,
			String countryCode, String areaCode, String number);

	List<Person> findByEmailAddressAndPhoneNumber(String email,
			String countryCode, String areaCode, String number, String extension);

	List<Person> findByEmailAddress(String email);

	List<Person> findByPhoneNumber(String countryCode, String areaCode, String number, String extension);

	List<Person> findByPhoneNumber(String countryCode, String areaCode, String number);

}
