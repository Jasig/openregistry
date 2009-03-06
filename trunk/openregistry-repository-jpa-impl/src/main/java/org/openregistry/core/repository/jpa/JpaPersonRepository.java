package org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Person repository implementation built on top of JPA.
 *
 * @author Dmitriy Kopylenko
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Repository
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Person findByInternalId(Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaPersonImpl.class, id);
    }

    public Person savePerson(Person person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }
}

