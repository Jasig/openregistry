/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.repository.jpa;

import org.openregistry.core.domain.sor.SystemOfRecord;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type.DataTypes;
import org.openregistry.core.domain.jpa.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Default implementation of temporary repository.
 */
@Repository(value = "referenceRepository")
public final class JpaReferenceRepository implements ReferenceRepository {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;


    public List<Person> getPeople() {
        return (List<Person>) this.entityManager.createQuery("select p from person p join p.names n where n.officialName = true order by n.family, n.given").getResultList();
    }


    public Person getPersonById(final Long id) {
        return this.entityManager.find(JpaPersonImpl.class, id);
    }


    public List<OrganizationalUnit> getOrganizationalUnits() {
        return (List<OrganizationalUnit>) this.entityManager.createQuery("select d from organizationalUnit d order by d.name").getResultList();
    }


    public OrganizationalUnit getOrganizationalUnitById(final Long id) {
        return this.entityManager.find(JpaOrganizationalUnitImpl.class, id);
    }


    public OrganizationalUnit getOrganizationalUnitByCode(String code) {
        return (OrganizationalUnit) this.entityManager.createQuery("select d from organizationalUnit d where d.localCode = :code order by d.name").setParameter("code", code).getSingleResult();
    }


    public List<Campus> getCampuses() {
        return (List<Campus>) this.entityManager.createQuery("select c from campus c order by c.name").getResultList();
    }


    public Campus getCampusById(final Long id) {
        return this.entityManager.find(JpaCampusImpl.class, id);
    }


    public Country getCountryById(final Long id) {
        return this.entityManager.find(JpaCountryImpl.class, id);
    }


    public Country getCountryByCode(String code) {
        return (Country) this.entityManager.createQuery("select c from country c where c.code = :code order by c.name").setParameter("code", code).getSingleResult();
    }


    public List<Country> getCountries() {
        return (List<Country>) this.entityManager.createQuery("select c from country c").getResultList();
    }


    public List<Region> getRegions() {
        return (List<Region>) this.entityManager.createQuery("select r from region r").getResultList();
    }

    public List<Region> getRegionsByCountryCode(final String countryCode) {
        return (List<Region>) this.entityManager.createQuery("select r from region r join r.country c where c.code = :countryCode order by r.name").setParameter("countryCode", countryCode).getResultList();
    }

    public Region getRegionByCodeAndCountryId(final String code, final String countryCode) {
        return (Region) this.entityManager.createQuery("select r from region r join r.country c where r.code = :code and c.code = :countryCode order by r.name, c.code").setParameter("code", code).setParameter("countryCode", countryCode).getSingleResult();
    }

    public Region getRegionByCodeOrName(final String code) {
        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        final CriteriaQuery<JpaRegionImpl> c = criteriaBuilder.createQuery(JpaRegionImpl.class);
        c.distinct(true);
        final Root<JpaRegionImpl> region = c.from(JpaRegionImpl.class);
        c.where(criteriaBuilder.or(criteriaBuilder.equal(region.get(JpaRegionImpl_.code), code), criteriaBuilder.like(region.get(JpaRegionImpl_.name), code)));

        try {
            return this.entityManager.createQuery(c).getSingleResult();
        }
        catch (final Exception e) {
            log.debug(e.getMessage(), e);
            return null;
        }
    }


    public Type getTypeById(final Long id) {
        return this.entityManager.find(JpaTypeImpl.class, id);
    }


    public List<Type> getTypesBy(final DataTypes type) {
        return (List<Type>) this.entityManager.createQuery("select r from type r where r.dataType = :dataType").setParameter("dataType", type.name()).getResultList();
    }


    public Type findType(final DataTypes type, final Enum value) {
        return findType(type, value.name());
    }


    public Type findType(final DataTypes type, final String value) {
        try {
            return (Type) this.entityManager.createQuery("select r from type r where r.dataType=:dataType and r.description=:description").setParameter("dataType", type.name()).setParameter("description", value).getSingleResult();
        }
        catch (final DataAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public List<IdentifierType> getIdentifierTypes() {
        return (List<IdentifierType>) this.entityManager.createQuery("select r from identifier_type r").getResultList();
    }


    public IdentifierType findIdentifierType(final String identifierName) {
        return (IdentifierType) this.entityManager.createQuery("select distinct r from identifier_type r where name=:name").setParameter("name", identifierName).getSingleResult();
    }


    public SystemOfRecord findSystemOfRecord(String systemOfRecord){
        return (SystemOfRecord) this.entityManager.createQuery("select s from systemOfRecord s where s.sorId = :systemOfRecord").setParameter("systemOfRecord", systemOfRecord).getSingleResult();
    }

    @Override
    public Type findValidType(DataTypes type, String value) {
        try {
            Type t = findType(type, value);
            return t;
        }
        catch (IllegalArgumentException e) {
            return null; 
        }
    }

    public void updateOrganizationalUnit(OrganizationalUnit orgUnit) {
        this.entityManager.merge(orgUnit);
    }
}
