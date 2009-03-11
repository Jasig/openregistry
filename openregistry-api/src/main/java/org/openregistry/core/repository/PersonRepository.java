package org.openregistry.core.repository;

import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;

/**
 * Repository abstraction to deal with persistence concerns for <code>Person</code> entities
 * and their related entities
 *
 * @since 1.0
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


}
