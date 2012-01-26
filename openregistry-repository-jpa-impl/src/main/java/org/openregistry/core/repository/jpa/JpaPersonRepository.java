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

import org.hibernate.loader.custom.Return;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.*;
import org.openregistry.core.domain.jpa.sor.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.*;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Person repository implementation built on top of JPA.
 *
 * @author Dmitriy Kopylenko
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Repository (value = "personRepository")
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext (unitName=  "OpenRegistryPersistence")
    private EntityManager entityManager;

    public Person findByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaPersonImpl.class, id);
    }

    public SorPerson findSorByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaSorPersonImpl.class, id);
    }

    public Person findByIdentifier(final String identifierType, final String identifierValue) throws RepositoryAccessException {
        return (Person) this.entityManager.createQuery("Select p from person p join p.identifiers i join i.type t where t.name = :name and i.value = :value").setParameter("name", identifierType).setParameter("value", identifierValue).getSingleResult();
    }

    @Override
    public List<Person> findByUnknownIdentifier(final String identifierValue) throws RepositoryAccessException {
        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        final CriteriaQuery<JpaPersonImpl> c =criteriaBuilder.createQuery(JpaPersonImpl.class);
        final Root<JpaPersonImpl> person = c.from(JpaPersonImpl.class);
        final Join<JpaPersonImpl,JpaIdentifierImpl> identifier = person.join(JpaPersonImpl_.identifiers);

        c.select(  person).distinct(true).where(criteriaBuilder.like(identifier.get(JpaIdentifierImpl_.value), identifierValue + "%"));

        final List<JpaPersonImpl> persons = this.entityManager.createQuery(c).getResultList();

        return new ArrayList<Person>(persons);
    }

    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSource) {
        return (SorPerson) this.entityManager.createQuery("Select s from sorPerson s where s.sourceSor = :sorSource and s.personId = :personId").setParameter("sorSource", sorSource).setParameter("personId", personId).getSingleResult();
    }
    
    public SorPerson findSorBySSN(final String ssn) {
        //It will be more than one person if the same person is coming from more than one resources
        List sorPersons = this.entityManager.createQuery("Select s from sorPerson s where s.ssn = :ssn").setParameter("ssn", ssn).getResultList();
        if (sorPersons.size() > 0)
            return (SorPerson) sorPersons.get(0);
        return null;
    }

    public List<Person> searchByCriteria(final SearchCriteria searchCriteria) throws RepositoryAccessException {
        final String givenName = searchCriteria.getGivenName();
        final String familyName = searchCriteria.getFamilyName();
        final Date birthDate = searchCriteria.getDateOfBirth();
        final String searchCriteriaName = searchCriteria.getName();

        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        final CriteriaQuery<JpaPersonImpl> c =criteriaBuilder.createQuery(JpaPersonImpl.class);
        c.distinct(true);
        final Root<JpaPersonImpl> person = c.from(JpaPersonImpl.class);
        final Join<JpaPersonImpl,JpaNameImpl> name = person.join(JpaPersonImpl_.names);
//        person.fetch(JpaPersonImpl_.names);
//        person.fetch(JpaPersonImpl_.roles);
//        person.fetch(JpaPersonImpl_.identifiers);


        final Predicate pBirthDate;
        if (birthDate != null) {
            pBirthDate = criteriaBuilder.equal(person.get(JpaPersonImpl_.dateOfBirth), birthDate);
        } else {
            pBirthDate = null;
        }

        final Predicate combined;

        if (StringUtils.hasText(givenName) && StringUtils.hasText(familyName)) {
            final Predicate pGivenName = criteriaBuilder.equal(name.get(JpaNameImpl_.given), givenName );
            final Predicate pFamilyName = criteriaBuilder.equal(name.get(JpaNameImpl_.family), familyName );

            combined = criteriaBuilder.and(pGivenName, pFamilyName);
        } else {
            final Predicate pGivenName = criteriaBuilder.equal(name.get(JpaNameImpl_.given), searchCriteriaName );
            final Predicate pFamilyName = criteriaBuilder.equal(name.get(JpaNameImpl_.family), searchCriteriaName );
            combined = criteriaBuilder.or(pGivenName, pFamilyName);
        }

        if (pBirthDate != null && combined != null) {
            c.select(person).where(criteriaBuilder.and(pBirthDate, combined));
        } else if (pBirthDate != null) {
            c.select(person).where(pBirthDate);
        } else {
            c.select(person).where(combined);
        }

        final List<JpaPersonImpl> persons = this.entityManager.createQuery(c).getResultList();

        return new ArrayList<Person>(persons);
    }

    public List<Person> findByFamilyName(final String family) throws RepositoryAccessException {
    	return this.entityManager.createQuery("SELECT p FROM person p JOIN fetch p.names n WHERE n.family = :name")
    	.setParameter("name", family).getResultList();
    }

    public List<Person> findByFamilyComparisonValue(final String familyComparisonValue) throws RepositoryAccessException {
         return this.entityManager.createQuery("SELECT p FROM person p JOIN fetch p.names n WHERE n.familyComparisonValue = :comparisonValue")
         .setParameter("comparisonValue", familyComparisonValue).getResultList();
     }


    public Person savePerson(final Person person) throws RepositoryAccessException {
        Person p= this.entityManager.merge(person);
        //the only solution of insert before delete problem
         this.entityManager.flush();
        return  p;
    }

    public SorPerson saveSorPerson(final SorPerson person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }

    public void deleteSorRole(final SorPerson person, final SorRole role) {
		SorRole sorRoleToDelete = this.entityManager.getReference(role.getClass(), role.getId());
		this.entityManager.remove(sorRoleToDelete);
        saveSorPerson(person);
    }

    public void updateRole(final Person person, final Role role) {
        this.entityManager.merge(person);
    }

    public SorRole saveSorRole(final SorRole role) throws RepositoryAccessException {
        return this.entityManager.merge(role);
    }

    public SorPerson findBySorIdentifierAndSource(final String sorSource, final String sorId) {
        return (SorPerson) this.entityManager.createQuery("select s from sorPerson s where s.sourceSor = :sorSource and s.sorId = :sorId").setParameter("sorSource", sorSource).setParameter("sorId", sorId).getSingleResult();
    }

    public void deleteSorPerson(final SorPerson sorPerson) {
        SorPerson sorPersonToDelete = this.entityManager.getReference(sorPerson.getClass(), sorPerson.getId());
        this.entityManager.remove(sorPersonToDelete);
    }

    /**
     * Returns the SoR records for a particular person.
     *
     * @param person the person
     * @return a list of sorPerson records.
     */
    public List<SorPerson> getSoRRecordsForPerson(final Person person){
        return (List<SorPerson>) this.entityManager.createQuery("select s from sorPerson s where s.personId = :personId").setParameter("personId", person.getId()).getResultList();
    }

    public Number getCountOfSoRRecordsForPerson(final Person person) {
        return (Number) this.entityManager.createQuery("select count(s) from sorPerson s where s.personId = :personId").setParameter("personId", person.getId()).getSingleResult();
    }

    public void deletePerson(final Person person) {
        this.entityManager.remove(person);
    }

    public List<Person> findByEmailAddressAndPhoneNumber(final String email, final String countryCode, final String areaCode, final String number, final String extension) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles r, IN(r.emailAddresses) e, IN(r.phones) ph where e.address = :email and ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number and ph.extension = :extension").setParameter("email", email).setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).setParameter("extension", extension).getResultList();
    }

    /* No extension */
    public List<Person> findByEmailAddressAndPhoneNumber(final String email, final String countryCode, final String areaCode, final String number) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles r, IN(r.emailAddresses) e, IN(r.phones) ph where e.address = :email and ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number").setParameter("email", email).setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).getResultList();
    }

    public List<Person> findByEmailAddress(final String email) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles r, IN(r.emailAddresses) e where e.address = :email").setParameter("email", email).getResultList();
    }

    public List<Person> findByPhoneNumber(final String countryCode, final String areaCode, final String number, final String extension) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles r, IN(r.phones) ph where ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number and ph.extension = :extension").setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).setParameter("extension", extension).getResultList();
    }

    /* No extension */
    public List<Person> findByPhoneNumber(final String countryCode, final String areaCode, final String number) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles r, IN(r.phones) ph where ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number").setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).getResultList();
    }

    /**
     * Expose underlying EntityManager
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public SorRole getSoRRoleForRole(Role calculatedRole){
         return  (SorRole) this.entityManager.createQuery("select r from sorRole r where r.id =:recordID").setParameter("recordID",calculatedRole.getSorRoleId()).getSingleResult();

    }
    public Role getCalculatedRoleForSorRole(SorRole sorRole){
        List roles = this.entityManager.createQuery("select r from role r where r.sorRoleId =:sorRecordID").setParameter("sorRecordID",sorRole.getId()).getResultList();
        if (roles.size()>0){
            return (Role) roles.get(0);
        
    }
        return null;
    }
}

