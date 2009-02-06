package org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Default implementation of temporary repository. 
 */
@Repository
public final class JpaReferenceRepositoryImpl implements ReferenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Person> getPeople() {
        return (List<Person>) this.entityManager.createQuery("select p from person p order by p.preferredName.family, p.preferredName.given").getResultList();
    }

    @Transactional
    public List<Department> getDepartments() {
        return (List<Department>) this.entityManager.createQuery("select d from department d order by d.name").getResultList(); 
    }

    @Transactional
    public Department getDepartmentById(final Long id) {
        return this.entityManager.find(JpaDepartmentImpl.class, id);
    }

    @Transactional
    public List<Campus> getCampuses() {
        return (List<Campus>) this.entityManager.createQuery("select c from campus c order by c.name").getResultList();
    }

    @Transactional
    public Campus getCampusById(final Long id) {
        return this.entityManager.find(JpaCampusImpl.class, id);
    }

    @Transactional
    public Country getCountryById(final Long id) {
        return this.entityManager.find(JpaCountryImpl.class, id);
    }

    @Transactional
    public List<Country> getCountries() {
        return (List<Country>) this.entityManager.createQuery("select c from country c").getResultList();
    }

    @Transactional
    public List<RoleInfo> getRoleInfos() {
        return (List<RoleInfo>) this.entityManager.createQuery("select r from roleInfo r order by r.title");
    }

    @Transactional
    public RoleInfo getRoleInfo(final Long id) {
        return this.entityManager.find(JpaRoleInfoImpl.class, id);
    }

    @Transactional
    public List<Region> getRegions() {
        return (List<Region>) this.entityManager.createQuery("select r from region r").getResultList();
    }

}
