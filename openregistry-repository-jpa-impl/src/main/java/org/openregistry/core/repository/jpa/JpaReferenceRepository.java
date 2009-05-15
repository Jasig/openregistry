package org.openregistry.core.repository.jpa;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.DataTypes;
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
        return (List<Person>) this.entityManager.createQuery("select p from person p join p.names n where n.officialName = true order by n.family, n.given").getResultList();
    }

    @Transactional
    public Person getPersonById(final Long id) {
        return (Person) this.entityManager.find(JpaPersonImpl.class, id);
    }

    @Transactional
    public List<OrganizationalUnit> getOrganizationalUnits() {
        return (List<OrganizationalUnit>) this.entityManager.createQuery("select d from organizationalUnit d order by d.name").getResultList();
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
        return (List<RoleInfo>) this.entityManager.createQuery("select r from roleInfo r order by r.title").getResultList();
    }

    @Transactional
    public RoleInfo getRoleInfoById(final Long id) {
        return this.entityManager.find(JpaRoleInfoImpl.class, id);
    }
    
    @Transactional
    public RoleInfo getRoleInfoByCode(final String code) {
    	return (RoleInfo)this.entityManager.createQuery("select r from roleInfo r where r.code = :code order by r.title").setParameter("code", code).getSingleResult();
    }
    
    @Transactional
    public RoleInfo getRoleInfoByOrganizationalUnitAndTitle(final OrganizationalUnit ou, final String title) {
       	return (RoleInfo)this.entityManager.createQuery("select r from roleInfo r where r.organizationalUnit = :ou and r.title = :title order by r.title").setParameter("ou", ou).setParameter("title", title).getSingleResult();
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
    public List<Type> getTypesBy(final DataTypes type) {
        return (List<Type>) this.entityManager.createQuery("select r from type r where r.dataType = :dataType").setParameter("dataType", type.name()).getResultList();
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
    public List<Type> getAffiliationTypes() {
        return (List<Type>) this.entityManager.createQuery("select r from type r where dataType='Affiliation'").getResultList();
    }

    @Transactional
    public Type findType(final DataTypes type, final String value) {
        return (Type) this.entityManager.createQuery("select r from type r where dataType=:dataType and description=:description").setParameter("dataType",type.name()).setParameter("description",value).getSingleResult();
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
