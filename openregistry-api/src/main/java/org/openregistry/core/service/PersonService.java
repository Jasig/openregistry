package org.openregistry.core.service;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;

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
}
