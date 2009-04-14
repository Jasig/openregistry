package org.openregistry.core.repository;

import java.util.List;

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
     * Finds the <code>Person</code> based on the identifier type and value.
     * @param identifierType the identifier type
     * @param identifierValue the identifier value.
     * @return the person.  CANNOT be null.
     * @throws RepositoryAccessException if the operation does not succeed for any number of
     *                                   technical reasons.
     */
    Person findByIdentifier(String identifierType, String identifierValue) throws RepositoryAccessException;

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

    void addPerson(Person person) throws RepositoryAccessException;

}
