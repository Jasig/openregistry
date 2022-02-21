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

    private final static int MAX_QUERY_LIMIT = 1000;

    public Person findByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaPersonImpl.class, id);
    }
    public Person fetchCompleteCalculatedPerson(Long id){
        return (Person) this.entityManager.createQuery("Select p from person " +
                " p left join fetch p.identifiers i left join fetch i.type left join fetch p.names left join fetch p.roles r left join fetch p.disclosureSettings d left join fetch r.urls  left join fetch r.emailAddresses  " +
                "left join fetch r.phones left join fetch r.addresses left join fetch r.organizationalUnit left join fetch d.addressDisclosureSettings " +
                "  left join fetch d.emailDisclosureSettings left join fetch d.phoneDisclosureSettings left join fetch d.urlDisclosureSettings  where p.id = :id").setParameter("id", id.longValue()).getSingleResult();

    }

    public SorPerson findSorByInternalId(final Long id) throws RepositoryAccessException {
        return this.entityManager.find(JpaSorPersonImpl.class, id);
    }

    public Person findByIdentifier(final String identifierType, final String identifierValue) throws RepositoryAccessException {
        if (Type.IdentifierTypes.RCN.toString().equalsIgnoreCase(identifierType)) {
            //return (Person) this.entityManager.createQuery("Select p from person p join p.idCards i where i.isPrimary and i.cardNumber = :value").setParameter("value", identifierValue).getSingleResult();
            return (Person) this.entityManager.createQuery("Select p from person p join p.idCards i where i.cardNumber = :value").setParameter("value", identifierValue).getSingleResult();
        } else {
            return (Person) this.entityManager.createQuery("Select p from person p join p.identifiers i join i.type t where t.name = :name and i.value = :value").setParameter("name", identifierType).setParameter("value", identifierValue).getSingleResult();
        }
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

        // search by role criteria
        final String sponsorNetID = searchCriteria.getSponsorNetID();
        final OrganizationalUnit organizationalUnit = searchCriteria.getOrganizationalUnit();
        final Date roleExpDate = searchCriteria.getRoleExpDate();
        final Type roleType = searchCriteria.getAffiliationType();


        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        final CriteriaQuery<JpaPersonImpl> c =criteriaBuilder.createQuery(JpaPersonImpl.class);
        c.distinct(true);
        final Root<JpaPersonImpl> person = c.from(JpaPersonImpl.class);
        final Join<JpaPersonImpl,JpaNameImpl> name = person.join(JpaPersonImpl_.names);
        final Join<JpaPersonImpl,JpaRoleImpl> role = person.join(JpaPersonImpl_.roles);

//        person.fetch(JpaPersonImpl_.names);
//        person.fetch(JpaPersonImpl_.roles);
//        person.fetch(JpaPersonImpl_.identifiers);


        final Predicate pBirthDate;
        if (birthDate != null) {
            pBirthDate = criteriaBuilder.equal(person.get(JpaPersonImpl_.dateOfBirth), birthDate);
        } else {
            pBirthDate = null;
        }

        Predicate combined = null;

        if (StringUtils.hasText(givenName) && StringUtils.hasText(familyName)) {
//          final Predicate pGivenName = criteriaBuilder.equal(name.get(JpaNameImpl_.given), givenName );
            Expression<String> pattern = criteriaBuilder.literal((String) givenName + "%");
            final Predicate pGivenName = criteriaBuilder.like(name.get(JpaNameImpl_.given), pattern);
            final Predicate pFamilyName = criteriaBuilder.equal(name.get(JpaNameImpl_.family), familyName );

            combined = criteriaBuilder.and(pGivenName, pFamilyName);
        } else if (StringUtils.hasText(givenName)) {
            combined = criteriaBuilder.equal(name.get(JpaNameImpl_.given), givenName);
        } else if (StringUtils.hasText(familyName)) {
            combined = criteriaBuilder.equal(name.get(JpaNameImpl_.family), familyName );
        } else if (StringUtils.hasText(searchCriteriaName)) {
            final Predicate pGivenName = criteriaBuilder.equal(name.get(JpaNameImpl_.given), searchCriteriaName );
            final Predicate pFamilyName = criteriaBuilder.equal(name.get(JpaNameImpl_.family), searchCriteriaName );
            combined = criteriaBuilder.or(pGivenName, pFamilyName);
        }


        Predicate pRoleCombined = null;

        if (roleType != null) {
            //final Join<JpaPersonImpl,JpaRoleImpl> role = person.join(JpaPersonImpl_.roles);
            //final Join<JpaPersonImpl,JpaRoleImpl> role = name.join(JpaPersonImpl_.roles);
            pRoleCombined = criteriaBuilder.equal(role.get(JpaRoleImpl_.affiliationType), roleType);
        }

        if (organizationalUnit != null && StringUtils.hasText(organizationalUnit.getName())) {
            Predicate orgUnitP = criteriaBuilder.equal(role.get(JpaRoleImpl_.organizationalUnit), organizationalUnit);
            if (pRoleCombined != null)
                pRoleCombined = criteriaBuilder.and(pRoleCombined, orgUnitP);
            else
                pRoleCombined =  orgUnitP;
            //pRoleCombined = criteriaBuilder.and(pRoleCombined, orgUnitP);
        }

        if (StringUtils.hasText(sponsorNetID)) {
            Person person1 = findByIdentifier(Type.IdentifierTypes.NETID.name(), sponsorNetID);
            if (person1 != null) {
                Predicate sponsorP = criteriaBuilder.equal(role.get(JpaRoleImpl_.sponsorId), person1.getId());
                if (pRoleCombined != null)
                    pRoleCombined = criteriaBuilder.and(pRoleCombined, sponsorP);
                else
                    pRoleCombined = sponsorP;
            } else {
                pRoleCombined = criteriaBuilder.or();
            }

        }
        if (roleExpDate != null) {
            Predicate expDateP = criteriaBuilder.between(role.get(JpaRoleImpl_.end), new Date(), roleExpDate);
            if (pRoleCombined != null)
                pRoleCombined = criteriaBuilder.and(pRoleCombined, expDateP);
            else
                pRoleCombined = expDateP;
        }


        Predicate pComplete = criteriaBuilder.and();

        if (pBirthDate != null)
            pComplete = criteriaBuilder.and(pComplete, pBirthDate);
        if (combined != null)
            pComplete = criteriaBuilder.and(pComplete, combined);
        if (pRoleCombined != null)
            pComplete = criteriaBuilder.and(pComplete, pRoleCombined);

        c.select(person).where(pComplete);

//        if (pBirthDate != null && combined != null) {
//            c.select(person).where(criteriaBuilder.and(pBirthDate, combined));
//        } else if (pBirthDate != null) {
//            c.select(person).where(pBirthDate);
//        } else {
//            c.select(person).where(combined);
//        }

        final List<JpaPersonImpl> persons = this.entityManager.createQuery(c).setMaxResults(MAX_QUERY_LIMIT).getResultList();

        return new ArrayList<Person>(persons);
    }

    public List<Person> findByFamilyName(final String family) throws RepositoryAccessException {
        return this.entityManager.createQuery("SELECT distinct p FROM person p JOIN  p.names n WHERE n.family = :name")
                .setParameter("name", family).getResultList();
    }

    public List<Person> findByFamilyComparisonValue(final String familyComparisonValue) throws RepositoryAccessException {
        return this.entityManager.createQuery("SELECT distinct p FROM person p JOIN  p.names n WHERE n.familyComparisonValue = :comparisonValue")
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
        this.entityManager.flush();
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

    public List<Person> findBySsnFirstNameDobGender(final String Ssn, final String firstName, final Date dob, final String gender) throws RepositoryAccessException {
        return this.entityManager.createQuery("select distinct p from person p, name n, identifier i where p.id = n.person and p.id = i.person and n.given = :firstName and i.value = :Ssn and p.dateOfBirth = :dob and p.gender = :gender")
                .setParameter("firstName", firstName).setParameter("Ssn",Ssn).setParameter("dob",dob).setParameter("gender",gender).getResultList();
    }

    public List<Person> findBySsnFirstNameMiddleInitDobGender(final String Ssn, final String firstName, final String middleInitial, final Date dob, final String gender) throws RepositoryAccessException {
        return this.entityManager.createQuery("select distinct p from person p, name n, identifier i where p.id = n.person and p.id = i.person and n.given = :firstName and n.middle = :middleInitial and i.value = :Ssn and p.dateOfBirth = :dob and p.gender = :gender")
                .setParameter("firstName", firstName).setParameter("middleInitial",middleInitial).setParameter("Ssn",Ssn).setParameter("dob",dob).setParameter("gender",gender).getResultList();
    }

}

