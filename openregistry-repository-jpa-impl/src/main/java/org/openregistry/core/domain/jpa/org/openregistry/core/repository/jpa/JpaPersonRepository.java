package org.openregistry.core.domain.jpa.org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.Person;

/**
 * Person repository implementation built on top of JPA.
 *
 * @since 1.0
 *        TODO: add implementation
 */
public class JpaPersonRepository implements PersonRepository {

    /**
     * @see org.openregistry.core.repository.PersonRepository#findByInternalId(Long) 
     */
    public Person findByInternalId(Long id) throws RepositoryAccessException {
        return null;
    }

    /**
     *
     * @see org.openregistry.core.repository.PersonRepository#savePerson(org.openregistry.core.domain.Person) 
     */
    public Person savePerson(Person person) throws RepositoryAccessException {
        return null;
    }
}
