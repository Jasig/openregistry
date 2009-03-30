package org.openregistry.core.repository.jpa;

import java.util.List;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public Person findByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaPersonImpl.class, id);
    }
    
    public List<Person> findByFamilyName(final String family) throws RepositoryAccessException {
    	Query query = this.entityManager.createQuery("SELECT person FROM Person person WHERE person.officialName.family = :family");
    	query.setParameter("name", family);
    	return query.getResultList();
    }

    public Person savePerson(final Person person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }

    public SorPerson saveSorPerson(final SorPerson person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }

    public void addPerson(Person person) throws RepositoryAccessException {
        this.entityManager.persist(person);
    }
}

