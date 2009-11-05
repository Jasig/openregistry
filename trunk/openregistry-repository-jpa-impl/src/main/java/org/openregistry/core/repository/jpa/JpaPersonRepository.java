/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.repository.jpa;

import java.util.List;
import java.util.Date;

import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.RepositoryAccessException;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.jpa.JpaPersonImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.service.SearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    @PersistenceContext
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

    public SorPerson findByPersonIdAndSorIdentifier(final Long personId, final String sorSource) {
        return (SorPerson) this.entityManager.createQuery("Select s from sorPerson s where s.sourceSor = :sorSource and s.personId = :personId").setParameter("sorSource", sorSource).setParameter("personId", personId).getSingleResult();
    }

    public SorRole findSorRoleByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaSorRoleImpl.class, id);
    }

    public List<Person> searchByCriteria(final SearchCriteria searchCriteria) throws RepositoryAccessException {
        final String givenName = searchCriteria.getGivenName();
        final String familyName = searchCriteria.getFamilyName();
        final Date birthDate = searchCriteria.getDateOfBirth();

        if (birthDate == null) {
            return this.entityManager.createQuery("select p from person p join p.names n where n.given like :given and n.family  like :family").setParameter("given", "%" + givenName + "%").setParameter("family", "%" + familyName + "%").getResultList();
        } else {
            return this.entityManager.createQuery("select p from person p join p.names n where n.given like :given and n.family  like :family and p.dateOfBirth = :dateOfBirth").setParameter("given", "%" + givenName + "%").setParameter("family", "%" + familyName + "%").setParameter("dateOfBirth", birthDate).getResultList();
        }
    }

    public List<Person> findByFamilyName(final String family) throws RepositoryAccessException {
    	return this.entityManager.createQuery("SELECT p FROM person p JOIN p.names n WHERE n.family = :name")
    	.setParameter("name", family).getResultList();
    }

    public Person savePerson(final Person person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }

    public SorPerson saveSorPerson(final SorPerson person) throws RepositoryAccessException {
        return this.entityManager.merge(person);
    }

    public void deleteSorRole(final SorPerson person, final SorRole role) {
        this.entityManager.merge(person);
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
  
    // TODO The next 5 methods are for supporting the new reconciler code....these should be replaced with something more general
    
    public List<Person> findByEmailAddressAndPhoneNumber(final String email, final String countryCode, final String areaCode, final String number, final String extension) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p where p.roles.emailAddresses.address = :email and p.roles.phones.countryCode = :countryCode and p.roles.phones.areaCode = :areaCode and p.roles.phones.number = :number and p.roles.phones.extension = :extension").setParameter("email", email).setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).setParameter("extension", extension).getResultList();
    }
    
    /* No extension */
    public List<Person> findByEmailAddressAndPhoneNumber(final String email, final String countryCode, final String areaCode, final String number) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p where p.roles.emailAddresses.address = :email and p.roles.phones.countryCode = :countryCode and p.roles.phones.areaCode = :areaCode and p.roles.phones.number = :number").setParameter("email", email).setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).getResultList();
    }
    
    public List<Person> findByEmailAddress(final String email) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles.emailAddresses e where e.address = :email").setParameter("email", email).getResultList();
    }
    
    public List<Person> findByPhoneNumber(final String countryCode, final String areaCode, final String number, final String extension) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles.phones ph where ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number and ph.extension = :extension").setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).setParameter("extension", extension).getResultList();
    }
    
    /* No extension */
    public List<Person> findByPhoneNumber(final String countryCode, final String areaCode, final String number) {
    	return (List<Person>) this.entityManager.createQuery("select p from person p join p.roles.phones ph where ph.countryCode = :countryCode and ph.areaCode = :areaCode and ph.number = :number").setParameter("countryCode", countryCode).setParameter("areaCode", areaCode).setParameter("number", number).getResultList();
    }
}

