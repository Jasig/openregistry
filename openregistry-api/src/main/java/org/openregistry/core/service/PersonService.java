package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.PersonSearch;
import org.openregistry.core.service.reconciliation.ReconciliationResult;

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
     * Validate, add and persist in the Open Registry a given Role for a given Person
     *
     * @param person a person to add a role to.
     * @param role   to validate and add to a given person.
     * @return an instance of a <code>ServiceExecutionResult</code> containing details
     *         of whether this operation succeeded or failed.
     */
    ServiceExecutionResult validateAndSaveRoleForPerson(Person person, Role role);

    /**
     * Validate, add and persist a Person in the Open Registry
     *
     * @param person a person to valiate and add.
     * @return an instance of a <code>ServiceExecutionResult</code> containing details
     *         of whether this operation succeeded or failed.
     */
    ServiceExecutionResult validateAndSavePerson(Person person);

    /**
     * Validate, add, and persist a person in the OpenRegistry system.
     * <p>
     * This method should attempt to reconcile the person you are adding.
     *
     * @param personSearch the person you are trying to add with their additional reconciliation data.
     * @param result the reconciliation result if they had already attempted to save this person. Let's the system know
     * that we already looked through the list of possibilities.  CAN be null.  But if its null, reconciliation should execute.
     * @return the result of the action.  If the action succeeded, the target object should be the new Person.  Otherwise, it
     * will be the {@link org.openregistry.core.domain.sor.PersonSearch} object.
     */
    ServiceExecutionResult addPerson(PersonSearch personSearch, ReconciliationResult result);
}
