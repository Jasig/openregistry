package org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Default implementation of temporary repository. 
 */
@Repository(value = "referenceRepository")
public final class JpaReferenceRepository implements ReferenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Person> getPeople() {
        return (List<Person>) this.entityManager.createQuery("select p from person p join p.names n where n.preferredName = 1 order by n.family, n.given").getResultList();
    }

    @Transactional
    public Person getPersonById(final Long id) {
        return (Person) this.entityManager.find(JpaPersonImpl.class, id);
    }

    @Transactional
    public List<OrganizationalUnit> getOrganizationalUnits() {
        return (List<OrganizationalUnit>) this.entityManager.createQuery("select d from organizational_unit d order by d.name").getResultList(); 
    }

    @Transactional
    public OrganizationalUnit getOrganizationalUnitById(final Long id) {
        return this.entityManager.find(JpaOrganizationalUnitImpl.class, id);
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

    @Transactional
    public Region getRegionById(final Long id) {
        return this.entityManager.find(JpaRegionImpl.class, id);
    }

    @Transactional
    public List<Type> getEmailTypes() {
        return (List<Type>) this.entityManager.createQuery("select r from type r where dataType='EmailAddress'").getResultList();
    }

    @Transactional
    public List<Type> getAddressTypes() {
        return (List<Type>) this.entityManager.createQuery("select r from type r where dataType='Address'").getResultList();
    }

    @Transactional
    public List<Type> getPhoneTypes() {
        return (List<Type>) this.entityManager.createQuery("select r from type r where dataType='Phone'").getResultList();
    }

    @Transactional
    public Type findType(String data_type, String description) {
        Query q = this.entityManager.createQuery("select distinct r from type r where dataType=:data_type and description=:description");
        q.setParameter("data_type", data_type);
        q.setParameter("description", description);
        Type result = (Type)q.getSingleResult();
        return result;
    }

    @Transactional
    public List<IdentifierType> getIdentifierTypes(){
        return (List<IdentifierType>) this.entityManager.createQuery("select r from identifier_type r").getResultList();
    }

    @Transactional
    public IdentifierType findIdentifierType(final String identifierName){
        final Query q = this.entityManager.createQuery("select distinct r from identifier_type r where name=:name").setParameter("name", identifierName);
        return (IdentifierType) q.getSingleResult();
    }

}
