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
package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.openregistry.core.service.reconciliation.PersonMatch;
import org.openregistry.core.service.reconciliation.ReconciliationException;

import java.util.List;

/**
 * Component defining the main public API for interacting with Open Registry Persons subsystem.
 * It acts as the main orginazing application service layer component
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
     * Find canonical <code>SorPerson</code> entity in the Open Registry.
     *
     * @param id a primary key internal identifier for a sorPerson in Open Registry.
     * @return sorPerson found in the Open Registry's person repository or null if no person
     *         exist in Open Registry for a given internal id.
     */
    SorPerson findSorPersonById(Long id);

    /**
     * Finds the canonical <code>Person</code> by one of the unique identifiers that the system is aware of.
     *
     * @param identifierType the type of identifier
     * @param identifierValue the value of the identifier
     * @return the person, if found.  NULL otherwise.
     */
    Person findPersonByIdentifier(String identifierType, String identifierValue);

    /**
     * Locates the original system of record record by a calculated identifier AND the source of the system of record.
     *
     * @param identifierType the type of the identifier
     * @param identifierValue the value of the identifier
     * @param sorSourceId the source
     * @return the SorPerson, if found.  Otherwise, NULL.
     */
    SorPerson findSorPersonByIdentifierAndSourceIdentifier(String identifierType, String identifierValue, String sorSourceId);

    /**
     * For a particular Sor Person record for the specified sourceSorIdentifier.
     * @param id person id
     * @param sourceSorIdentifier
     * @return  SorPerson
     */
    SorPerson findByPersonIdAndSorIdentifier(Long id, String sourceSorIdentifier);

    /**
     * For a particular Sor Person record for the specified sourceSorIdentifier.
     * @param fromPerson
     * @param toPerson
     * @return  SorPerson
     */
    SorPerson hasSorRecordFromSameSource(Person fromPerson, Person toPerson);

    /**
     * Deletes a role.  Currently, this removes the record from the System of Record, and updates the "termination" date of the
     * calculated role.
     *
     * @param person the person who's role is to be deleted. CANNOT be null.
     * @param role the portions of the person to delete.  CANNOT be null.
     * @param terminationReason the reason that this role is being deleted.
     * @return true, if the action succeeded, false otherwise.
     * @throws IllegalArgumentException if an invalid parameter is passed in (i.e. an invalid termination reason).
     */
    boolean deleteSorRole(Person person, Role role, String terminationReason) throws IllegalArgumentException;

    /**
     * Deletes the system of record role.  Currently, this removes the record from the System of Record and updates the "termination" date of the
     * calculated role.
     *
     * @param person the person who's role is to be deleted. CANNOT be null.
     * @param role the portions of the person to delete.  CANNOT be null.
     * @param terminationReason the reason that this role is being deleted.
     * @return true, if the action succeeded, false otherwise.
     * @throws IllegalArgumentException if an invalid parameter is passed in (i.e. an invalid termination reason).
     */
    boolean deleteSorRole(SorPerson person, SorRole role, String terminationReason) throws IllegalArgumentException;

    /**
     * Removes the System of Record person from the repository.  A system may wish to do this when it no longer asserts the person
     * (i.e. they aren't taking classes anymore).  While its more likely, they would just leave the person, its possible some do
     * periodic clean ups of their data sources.  Having this information will allow us to more accurately "calculate" the actual
     * person the system knows about by discounting out-of-date information from systems of records.
     *
     * @param sorPerson the person to remove from the system of record.
     * @return true if it succeeded, false otherwise.
     */
    boolean deleteSystemOfRecordPerson(SorPerson sorPerson);

    /**
     * See {@link PersonService#deleteSystemOfRecordPerson(org.openregistry.core.domain.sor.SorPerson)}.  This is a convenience
     * method on top of that.
     *
     * @param sorSourceIdentifier the source name for the system of record (i.e or-webapp)
     * @param sorId the identifier.
     * @return true if it succeeded, false otherwise.
     */
    boolean deleteSystemOfRecordPerson(String sorSourceIdentifier, String sorId);

    /**
     * Removes a person from the repository. A person CAN only be removed IF there are no System of Record people associated
     * with the person, otherwise they would just come back!
     *
     * @param person the person to remove
     * @return true if it succeeded, false otherwise.
     */
    boolean deletePerson(Person person);

    /**
     * Validate, add and persist in the Open Registry a given Role for a given Person
     *
     * @param sorPerson a person to add a role to.
     * @param sorRole   to validate and add to a given person.
     * @return an instance of a <code>ServiceExecutionResult</code> containing details
     *         of whether this operation succeeded or failed.
     */
    ServiceExecutionResult<SorRole> validateAndSaveRoleForSorPerson(SorPerson sorPerson, SorRole sorRole);

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
    ServiceExecutionResult<Person> addPerson(ReconciliationCriteria reconciliationCriteria) throws ReconciliationException, IllegalArgumentException;

    /**
     * Forces the addition of a person despite there being an issue with Reconciliation.  Note, that implementations may STILL re-run reconciliation
     * to ensure that no one has changed the data in the ReconciliationCriteria.
     *
     * @param reconciliationCriteria the person you are trying to add with their additional reconciliation data.
     * @param reconciliationResult the reconciliation result if they had already attempted to save this person.
     * Let's the system know that we already looked through the list of possibilities.   CANNOT be NULL.
     * @return the result of the action, most likely the newly added Person.
     * @throws IllegalArgumentException if one of the arguments is missing.
    */
    ServiceExecutionResult<Person> forceAddPerson(ReconciliationCriteria reconciliationCriteria, ReconciliationResult reconciliationResult) throws IllegalArgumentException;

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
    ServiceExecutionResult<SorPerson> updateSorPerson(final SorPerson sorPerson);

    /**
     * Updates the SorRole.
     *
     * @param role to update
     * @return Result of updating.  Validation errors if they occurred or the sorPerson.
     */
    ServiceExecutionResult<SorRole> updateSorRole(final SorRole role);

    /**
     * Removes an SorName.
     *
     * @param sorPerson the person who's name to remove.
     * @param nameId id of name to delete from the person
     * @return Result of the remove. 
     */
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
}
