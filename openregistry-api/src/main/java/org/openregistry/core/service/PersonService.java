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

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.service.reconciliation.*;

import java.util.*;

/**
 * Component defining the main public API for interacting with Open Registry Persons subsystem.
 * It acts as the main organizing application service layer component
 * for the Persons subsystem of the Open Registry.
 *
 * @since 1.0
 */
public interface PersonService {

    /**
     * Find canonical <code>Person</code> entity in the Open Registry.
     *
     * @param id a primary key internal identifier for a person in Open Registry.
     * @return person found in the Open Registry's person repository or null if no person
     *         exist in Open Registry for a given internal id.
     */
    Person findPersonById(Long id);


    /**
     * Find canonical <code>Person</code> entity in the Open Registry.
     *  this method eargerly fetch the complete calculated object that is the only difference
     *  between this method and method findPersonById(id)
     *
     * @param id a primary key internal identifier for a person in Open Registry.
     * @return person found in the Open Registry's person repository or null if no person
     *  exist in Open Registry for a given internal id.

     */

         Person fetchCompleteCalculatedPerson(Long id);

    /**
     * Finds the canonical <code>Person</code> by one of the unique identifiers that the system is aware of.
     *
     * @param identifierType the type of identifier
     * @param identifierValue the value of the identifier
     * @return the person, if found.  NULL otherwise.
     */
    Person findPersonByIdentifier(final String identifierType, final String identifierValue);

    /**
     * For a particular Sor Person record for the specified sourceSorIdentifier.
     * @param id person id
     * @param sourceSorIdentifier
     * @return  SorPerson
     */
    SorPerson findByPersonIdAndSorIdentifier(Long id, String sourceSorIdentifier);

    /**
     * For a particular Sor Person record for the specified sourceSorIdentifier.
     * @param sorSource
     * @param sorId
     * @return  SorPerson
     */

    SorPerson findBySorIdentifierAndSource(final String sorSource, final String sorId);

    /**
     * Find SorPerson by a particular calculated person identifier and sor source id
     * @param identifierType the type of identifier
     * @param identifierValue the value of the identifier
     * @param sorSource
     * @return  SorPerson if found or null otherwise
     */
    SorPerson findByIdentifierAndSource(final String identifierType, final String identifierValue, final String sorSource);

    List<SorPerson> findByIdentifier(final String identifierType, final String identifierValue);

    /**
     * Retrieves the SoR Records for a particular calculated person.
     *
     * @param person the calculated person.  CANNOT BE NULL.
     * @return the list of records.  CANNOT be NULL. CAN BE EMPTY if personId is invalid.
     */
    List<SorPerson> getSorPersonsFor(Person person);

    /**
     * Retrieves the SoR Records for a particular calculated person.
     *
     * @param personId the id of the calculated person.
     * @return the list of records.  CANNOT be NULL. CAN BE EMPTY if personId is invalid.
     */
    List<SorPerson> getSorPersonsFor(Long personId);

    /**
     * Removes the System of Record person from the repository.  A system may wish to do this when it no longer asserts the person
     * (i.e. they aren't taking classes anymore).  While its more likely, they would just leave the person, its possible some do
     * periodic clean ups of their data sources.  Having this information will allow us to more accurately "calculate" the actual
     * person the system knows about by discounting out-of-date information from systems of records.
     * <p>
     * Its recommended, but not required, that individuals roles be expired or deleted first.  Otherwise, they will be closed or deleted
     * without a proper reason.
     *
     * @param sorPerson the person to remove from the system of record.
     * @param mistake whether the person should have existed or not. This MAY affect recalculation of information about the person.
     * @param terminationTypes the optional termination type for removed role records.  Unused when mistake=true.
     * @return true if it succeeded, false otherwise.
     * @throws IllegalArgumentException if the sorPerson is null.
     */
    boolean deleteSystemOfRecordPerson(SorPerson sorPerson, boolean mistake, String terminationTypes) throws IllegalArgumentException;

    /**
     * See {@link PersonService#deleteSystemOfRecordPerson(org.openregistry.core.domain.sor.SorPerson, boolean, String)}.  This is a convenience
     * method on top of that.
     *
     * @param sorSource the source name for the system of record (i.e or-webapp)
     * @param sorId the identifier.
     * @param mistake whether this person should have existed or not.
     * @param terminationTypes the optional termination type for removed role records.  Unused when mistake=true.
     * @return true if it succeeded, false otherwise.
     * @throws IllegalArgumentException if the sorSource or sorId is null
     * @throws PersonNotFoundException if the person could not be found from the sorSource and sorId
     */
    boolean deleteSystemOfRecordPerson(String sorSource, String sorId, boolean mistake, String terminationTypes) throws PersonNotFoundException, IllegalArgumentException;

	/**
	 * Removes the System of Record Role from the respository. Systems may wish to clean up their data by removing roles, although they could just set expiry dates on them
	 * @param sorPerson the System of Record person that the sorRole belongs to
	 * @param sorRole the object to be deleted from the repository
	 * @param mistake true if the SoR Role should never have existed
     * @param terminationTypes the optional termination type for removed role records.  Unused when mistake=true.
	 * @return true if the delete succeeds
	 * @throws IllegalArgumentException if the sorRole is null
	 */
	boolean deleteSystemOfRecordRole(SorPerson sorPerson, SorRole sorRole, boolean mistake, String terminationTypes) throws IllegalArgumentException;

    /**
     * Validate, add and persist in the Open Registry a given Role for a given Person
     *
     * @param sorPerson a person to add a role to.
     * @param sorRole   to validate and add to a given person.
     * @return an instance of a <code>ServiceExecutionResult</code> containing details
     *         of whether this operation succeeded or failed.
     * @throws IllegalArgumentException if any of the parameters are missing
     */
    ServiceExecutionResult<SorRole> validateAndSaveRoleForSorPerson(SorPerson sorPerson, SorRole sorRole) throws IllegalArgumentException;

     /**
     * Validate and persist a person and a new role in the OpenRegistry system.
     * If it is a completely new person, add the sorPerson and the calculated person with the new role.
     * If the calculated person exists, but the sorPerson with the same source doesn't, create the Sor person with the new role and link it to the calculated person.
     * If the sorPerson with the same source exists,throw IllegalArgumentException. Should call validateAndSaveRoleForSorPerson to add a new role to an existing SorPerson
     *
     * <p>
     * This method should attempt to reconcile the person you are adding.
     *
     * @param reconciliationCriteria the person you are trying to add with their additional reconciliation data.
     * @return the result of the action.  If the action succeeded, the target object should be the new Person.  If it failed, there should be > 0 validation errors
     * @throws IllegalArgumentException if the reconciliationCriteria is not provided with correct data.
     */
    ServiceExecutionResult<Person> validateAndSavePersonAndRole(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException;

    /**
     * Validate, add, and persist a person in the OpenRegistry system.
     * <p>
     * This method should attempt to reconcile the person you are adding.
     *
     * @param reconciliationCriteria the person you are trying to add with their additional reconciliation data.
     * @return the result of the action.  If the action succeeded, the target object should be the new Person.  If it failed, there should be > 0 validation errors
     * @throws IllegalArgumentException if the reconciliationCriteria is not provided.
     * @throws ReconciliationException if the person could not be added due to reconciliation.
     */
    ServiceExecutionResult<Person> addPerson(ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException, SorPersonAlreadyExistsException;

    /**
     * Forces the addition of a person despite there being an issue with Reconciliation.  Note, that implementations may STILL re-run reconciliation
     * to ensure that no one has changed the data in the ReconciliationCriteria.
     *
     * @param reconciliationCriteria the person you are trying to add with their additional reconciliation data.
     * @return the result of the action, most likely the newly added Person.
     * @throws IllegalArgumentException if reconciliationCriteria is missing.
     * @throws IllegalStateException if the reconciliationCriteria has not been seen before.
    */
    ServiceExecutionResult<Person> forceAddPerson(ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException, IllegalStateException;

    /**
     * Creates a new person for this SoR and links them to the calculated person that is provided, assuming they were in the original reconciliation
     * result.
     *
     * @param reconciliationCriteria the criteria of the person to add.
     * @param person the calculated person to link to.
     * @return the calculated person.
     * @throws IllegalArgumentException if any of the parameters are null.
     * @throws IllegalStateException if the reconciliation criteria/result combination could not be located.
     */
    ServiceExecutionResult<Person> addPersonAndLink(ReconciliationCriteria reconciliationCriteria, Person person) throws IllegalArgumentException, IllegalStateException, SorPersonAlreadyExistsException;

     /**
     * Run the reconciliation logic with the reconciliation criteria and return the reconciliation result.
     *
     * @param reconciliationCriteria Reconciliation criteria.
     * @return the reconciliation result
     * @throws IllegalArgumentException if any of the parameters are null.
     * @throws IllegalStateException if the reconciliation criteria/result combination could not be located.
     */
    ServiceExecutionResult<ReconciliationResult> reconcile(final ReconciliationCriteria reconciliationCriteria) throws IllegalArgumentException;

    /**
     * Searches for a Person by the criteria provided.
     *
     * @param searchCriteria the Person to search for.  CANNOT be null.
     * @return the list of matches for a particular person.  This list CANNOT be null, but may be empty.  The list MUST be sorted in order of Confidence.
     */
    List<PersonMatch> searchForPersonBy(SearchCriteria searchCriteria);

    /**
     * Updates the SorPerson.
     *
     * @param sorPerson the Person to update.
     * @return Result of updating.  Validation errors if they occurred or the sorPerson.
     */
    ServiceExecutionResult<SorPerson> updateSorPerson(SorPerson sorPerson);

    /**
     * Updates the SorRole for a particular person.
     *
     * @param sorPerson the person who's role we are updating.  CANNOT BE NULL.
     * @param role the role we are updating.  CANNOT BE NULL.
     * @return Result of updating.  Validation errors if they occurred or the sorPerson.
     */
    ServiceExecutionResult<SorRole> updateSorRole(SorPerson sorPerson, SorRole role);

    /**
     * Removes an SorName.
     *
     * @param sorPerson the person who's name to remove.
     * @param nameId id of name to delete from the person
     * @return Result of the remove.
     */
    // TODO this should be replace with a proper call to sorPerson.getNames().remove(index)
    boolean removeSorName(SorPerson sorPerson, Long nameId);

    /**
     * Move All Sor Records from one person to another.
     *
     * @param fromPerson person losing sor records.
     * @param toPerson person receiving sor records.
     * @return Result of move. Validation errors if they occurred or the Person receiving sor records.
     */
    boolean moveAllSystemOfRecordPerson(Person fromPerson, Person toPerson);

    /**
     * Move one Sor Record from one person to another.
     *
     * @param fromPerson person losing sor record.
     * @param toPerson person receiving sor record.
     * @param sorPerson record that is moving.
     * @return Result of move. Validation errors if they occurred or the to Person receiving sor records.
     */
    boolean moveSystemOfRecordPerson(Person fromPerson, Person toPerson, SorPerson sorPerson);

    /**
     * Move one Sor Record from one person to another where the to person is not in the registry
     *
     * @param fromPerson person losing sor record.
     * @param sorPerson record that is moving.
     * @return Result of move.
     */
    boolean moveSystemOfRecordPersonToNewPerson(Person fromPerson, SorPerson sorPerson);

    /**
     * Expire role immediately.
     *
     * @param role to expire.
     * @return Result.
     */
    // TODO should these be on the domain object?
    boolean expireRole(SorRole role);

     /**
     * Renew a role for the standard renewal.
     *
     * @param role to renew.
     * @return Result.
     */
     // TODO should these be on the domain object?
    boolean renewRole(SorRole role);

    /**
     * add or update preferred name
     *
     * @param person the Person to update.
     * @param sorPerson the SorPerson to update.
     * @return Result of updating.
     */
    boolean addOrUpdateChosenName(Person person, SorPerson sorPerson, String referredName);
}
