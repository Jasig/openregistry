package org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Person repository implementation built on top of JPA.
 *
 * @since 1.0
 *        TODO: review the implementation
 */
@Repository
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;
    

    /**
     * @see org.openregistry.core.repository.PersonRepository#findByInternalId(Long)
     */
    public Person findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(Person.class, id);
    }

    /**
     *
     * @see org.openregistry.core.repository.PersonRepository#savePerson(org.openregistry.core.domain.Person)
     */
    public Person savePerson(Person person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }
}

