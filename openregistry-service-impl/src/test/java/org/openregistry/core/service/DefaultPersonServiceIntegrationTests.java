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

package org.openregistry.core.service;

import static org.junit.Assert.*;

import org.jasig.openregistry.test.domain.MockSorPerson;
import org.jasig.openregistry.test.domain.MockSorRole;
import org.junit.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.jpa.sor.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.reconciliation.*;
import org.springframework.context.MessageSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.*;

import javax.inject.*;
import javax.persistence.*;
import javax.validation.ConstraintViolation;

import java.text.*;
import java.util.*;

/**
 * Integration test for {@link DefaultPersonService} that links up with the JPA
 * repositories.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 *
 */

@ContextConfiguration(locations = {"classpath:test-personServices-context.xml"})
public final class DefaultPersonServiceIntegrationTests extends AbstractIntegrationTests {

	private final String OR_WEBAPP_IDENTIFIER = "or-webapp";
	private final String REGISTRAR_IDENTIFIER = "registrar";

	private static final String EMAIL_ADDRESS = "test@test.edu";
	private static final String PHONE_NUMBER = "555-555-5555";
	private static final String RUDYARD = "Rudyard";
	private static final String KIPLING = "Kipling";
	private static final String RUDY = "Rudy";
	private static final String KIPSTEIN = "Kipstein";

    @Inject
    private PersonService personService;

    @Inject
    private ReferenceRepository referenceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    protected ReconciliationCriteria constructReconciliationCriteria(final String firstName, final String lastName, final String ssn, final String emailAddress, final String phoneNumber, Date birthDate, final String sor, final String sorId) {
        final ReconciliationCriteria reconciliationCriteria = new JpaReconciliationCriteriaImpl();
        reconciliationCriteria.setEmailAddress(emailAddress);
        reconciliationCriteria.setPhoneNumber(phoneNumber);

        final SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        sorPerson.setDateOfBirth(birthDate);
        sorPerson.setGender("M");
        sorPerson.setSorId(sorId);
        sorPerson.setSourceSor(sor);
        sorPerson.setSsn(ssn);

        final SorName name = sorPerson.addName(this.referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setFamily(lastName);
        name.setGiven(firstName);

        return reconciliationCriteria;
    }



    // test delete SoR Person no mistake
     @Test
     public void testDeleteSoRPersonNoMistakeOneSoR() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.addPerson(criteria);
        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult.getTargetObject().getId(), OR_WEBAPP_IDENTIFIER);

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        assertTrue(this.personService.deleteSystemOfRecordPerson(sorPerson, false, null));

        this.entityManager.flush();

        assertEquals(0, countRowsInTable("prs_sor_persons"));
        assertEquals(0, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
     }

    /**
     * Test 1: Test of adding a new SoR Person to an empty database:
     * Expectations: 1 SoR Person row created
     *               1 Calculated Person row created, one name, one identifier
     */
	@Test
	public void testAddOnePerson() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);

        assertTrue(result.succeeded());
        assertNotNull(result.getTargetObject().getId());
        this.entityManager.flush();
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
	}

   /**
     * Test 2: Test of adding two new SoR Persons to an empty database (with no matches):
     * Expectations: 2 Sor Person rows
     *               2 Calculated persons, two names, two identifiers
     */
    @Test
    public void testAddTwoDifferentPeople() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> result1 = this.personService.addPerson(reconciliationCriteria1);

        final ReconciliationCriteria reconciliationCriteria2 = constructReconciliationCriteria("Foo", "Bar", null, "la@lao.com", "9085550987", new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> result2 = this.personService.addPerson(reconciliationCriteria2);

        assertTrue(result2.succeeded());
        assertNotNull(result2.getTargetObject().getId());
        assertEquals(2, countRowsInTable("prc_persons"));
        assertEquals(2, countRowsInTable("prc_names"));
        assertEquals(2, countRowsInTable("prs_names"));
        assertEquals(2, countRowsInTable("prs_sor_persons"));

		final Person person2 = result2.getTargetObject();
        final SorPerson sorPerson2 = this.personService.findByPersonIdAndSorIdentifier(person2.getId(), OR_WEBAPP_IDENTIFIER);

		final Person person1 = result1.getTargetObject();
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(person1.getId(), OR_WEBAPP_IDENTIFIER);

		// check birthdate is set correctly
		Date birthDate1 = this.simpleJdbcTemplate.queryForObject("select date_of_birth from prc_persons where id = ?", Date.class, person1.getId());
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
		assertEquals(formatter.format(birthDate1),formatter.format(person1.getDateOfBirth()));

		Date birthDate2 = this.simpleJdbcTemplate.queryForObject("select date_of_birth from prc_persons where id = ?", Date.class, person2.getId());
		assertEquals(formatter.format(birthDate2),formatter.format(person2.getDateOfBirth()));

		// check SOR source is set correctly
		String sourceSor1 = this.simpleJdbcTemplate.queryForObject("select source_sor_id from prs_sor_persons where person_id = ?", String.class, person1.getId());
		assertEquals(sourceSor1,sorPerson1.getSourceSor());

		String sourceSor2 = this.simpleJdbcTemplate.queryForObject("select source_sor_id from prs_sor_persons where person_id = ?", String.class, person2.getId());
		assertEquals(sourceSor2,sorPerson2.getSourceSor());


		// check names in prc_names
		String familyName1 = this.simpleJdbcTemplate.queryForObject("select family_name from prc_names where person_id = ?", String.class, person1.getId());
		assertEquals(familyName1,KIPLING);

		String familyName2 = this.simpleJdbcTemplate.queryForObject("select family_name from prc_names where person_id = ?", String.class, person2.getId());
		assertEquals(familyName2,"Bar");

		String givenName1 = this.simpleJdbcTemplate.queryForObject("select given_name from prc_names where person_id = ?", String.class, person1.getId());
		assertEquals(givenName1,RUDYARD);

		String givenName2 = this.simpleJdbcTemplate.queryForObject("select given_name from prc_names where person_id = ?", String.class, person2.getId());
		assertEquals(givenName2,"Foo");

		// check names in prs_names
		String prsFamilyName1 = this.simpleJdbcTemplate.queryForObject("select family_name from prs_names where sor_person_id = ?", String.class, sorPerson1.getId());
		assertEquals(prsFamilyName1,KIPLING);

		String prsGivenName1 = this.simpleJdbcTemplate.queryForObject("select given_name from prs_names where sor_person_id = ?", String.class, sorPerson1.getId());
		assertEquals(prsGivenName1,RUDYARD);

		String prsFamilyName2 = this.simpleJdbcTemplate.queryForObject("select family_name from prs_names where sor_person_id = ?", String.class, sorPerson2.getId());
		assertEquals(prsFamilyName2,"Bar");

		String prsGivenName2 = this.simpleJdbcTemplate.queryForObject("select given_name from prs_names where sor_person_id = ?", String.class, sorPerson2.getId());
		assertEquals(prsGivenName2,"Foo");

    }

    /**
     * Test 3: Test of adding two new Sor Persons where there is an exact match (same SoR)
     *
     * This is an update.  TODO complete this test
     */
    @Test(expected = SorPersonAlreadyExistsException.class)
    @Rollback
    public void testAddExactPersonWithSameSoR() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);
        final Person person = result.getTargetObject();

        assertTrue(result.succeeded());
        assertNotNull(result.getTargetObject().getId());
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));

        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(person.getId(), reconciliationCriteria.getSorPerson().getSourceSor());

        // check birthdate is set correctly
        Date birthDate = this.simpleJdbcTemplate.queryForObject("select date_of_birth from prc_persons where id = ?", Date.class, person.getId());
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
        assertEquals(formatter.format(birthDate),formatter.format(person.getDateOfBirth()));

        // check SOR source is set correctly
        String sourceSor = this.simpleJdbcTemplate.queryForObject("select source_sor_id from prs_sor_persons where person_id = ?", String.class, person.getId());
        assertEquals(sourceSor,sorPerson.getSourceSor());

        // check names in prc_names
        String familyName = this.simpleJdbcTemplate.queryForObject("select family_name from prc_names where person_id = ?", String.class, person.getId());
        assertEquals(familyName,KIPLING);

        String givenName = this.simpleJdbcTemplate.queryForObject("select given_name from prc_names where person_id = ?", String.class, person.getId());
        assertEquals(givenName,RUDYARD);

        // check names in prs_names
        String prsFamilyName = this.simpleJdbcTemplate.queryForObject("select family_name from prs_names where sor_person_id = ?", String.class, sorPerson.getId());
        assertEquals(prsFamilyName,KIPLING);

        String prsGivenName = this.simpleJdbcTemplate.queryForObject("select given_name from prs_names where sor_person_id = ?", String.class, sorPerson.getId());
        assertEquals(prsGivenName,RUDYARD);


        this.personService.addPerson(reconciliationCriteria);
    }

    /**
     * Test 4: Test of adding two new SoR Persons where there is an exact match (different SoR)
     */
    @Test
    @Rollback
    public void testAddExactPersonWithDifferentSoRs() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "SOR2", null);
        this.personService.addPerson(reconciliationCriteria);

        final ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria1);

        assertTrue(result.getTargetObject() instanceof Person);
        assertTrue(result.succeeded());
        this.entityManager.flush();
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(2, countRowsInTable("prs_names"));
        assertEquals(2, countRowsInTable("prs_sor_persons"));
    }

    /**
     * Test 5: Test of adding two new SoR Persons where there is a partial match (same SoRs).
     * The test requires you to say its the same person.
     *
     * Expectation: kick us out this is an update!
     *
     */
    @Test(expected=SorPersonAlreadyExistsException.class)
    @Rollback
    public void testAddTwoSoRPersonsWithPartialMatchFromTheSameSoRWhereItsTheSamePerson() throws SorPersonAlreadyExistsException{
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria("FOOBAR", KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);

        ServiceExecutionResult<Person> serviceExecutionResult = null;
        try {
            serviceExecutionResult =  this.personService.addPerson(reconciliationCriteria);
            this.personService.addPerson(reconciliationCriteria1);
        } catch (final ReconciliationException e) {
            this.personService.addPersonAndLink(reconciliationCriteria1, serviceExecutionResult.getTargetObject());
        }
    }

    /**
     * Test 6: Test of adding two new SoR Persons where there is a partial match (same SoRs).
     * The test requires you to say its different people.
     *
     * Expectation: two SoR Records, and two Calculated People
     */
    @Test
    @Rollback
    public void testAddTwoSoRPersonsWithPartialMatchAndItsTwoDifferentPeopleFromSameSoR() throws SorPersonAlreadyExistsException{
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria("FOOBAR", KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);

        try {
            this.personService.addPerson(reconciliationCriteria);
        } catch (final ReconciliationException e) {
            // nothing to do
        }
        assertEquals(1, countRowsInTable("prs_sor_persons"));

        try {
            this.personService.addPerson(reconciliationCriteria1);
        } catch (final ReconciliationException e) {
            assertEquals(1, countRowsInTable("prs_sor_persons"));
            final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.forceAddPerson(reconciliationCriteria1);
            assertNotNull(serviceExecutionResult.getTargetObject());
            assertNotNull(serviceExecutionResult.getTargetObject().getId());
            assertEquals(2, countRowsInTable("prc_persons"));
            assertEquals(2, countRowsInTable("prc_names"));
            assertEquals(2, countRowsInTable("prs_names"));
            assertEquals(2, countRowsInTable("prs_sor_persons"));
        }
    }

    /**
     * Test 7: Test of adding two new SoR Persons where there is a partial match (different SoRs)
     * The test requires you to say its the same person.
     *
     * Expectation: we should add a new SoR Record and update existing calculated person.
     */
    @Test
    @Rollback
    public void testAddTwoSorPersonWithPartialMatchFromDifferentSoRsWhereItsTheSamePerson() throws SorPersonAlreadyExistsException{
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria("FOOBAR", KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "SOR2", null);

        ServiceExecutionResult<Person> serviceExecutionResult = null;
        try {
            serviceExecutionResult =  this.personService.addPerson(reconciliationCriteria);
            this.personService.addPerson(reconciliationCriteria1);
        } catch (final ReconciliationException e) {
            final ServiceExecutionResult<Person> ser = this.personService.addPersonAndLink(reconciliationCriteria1, serviceExecutionResult.getTargetObject());
            assertNotNull(ser.getTargetObject());
            entityManager.flush();
            assertEquals(1, countRowsInTable("prc_persons"));
            assertEquals(2, countRowsInTable("prs_names"));
            assertEquals(2, countRowsInTable("prs_sor_persons"));
        }
    }

    /**
     * Test 8: Test of adding two new SoR Persons where there is a partial match (different SoRs).
     * The test requires you to say its a different person.
     *
     * Expectation: a new SoR Person and Calculated Person will be created (2 of each).
     */
    @Test
    @Rollback
    public void testAddTwoNewSoRPersonsWithPartialMatchWhoAreDifferentPeople() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria reconciliationCriteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ReconciliationCriteria reconciliationCriteria1 = constructReconciliationCriteria("FOOBAR", KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "SOR2", null);

        this.personService.addPerson(reconciliationCriteria);
        assertEquals(1, countRowsInTable("prs_sor_persons"));

        try {
            this.personService.addPerson(reconciliationCriteria1);
        } catch (final ReconciliationException e) {
            assertEquals(1, countRowsInTable("prs_sor_persons"));
            final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.forceAddPerson(reconciliationCriteria1);
            assertNotNull(serviceExecutionResult.getTargetObject().getId());
            assertEquals(2, countRowsInTable("prc_persons"));
            assertEquals(2, countRowsInTable("prc_names"));
            assertEquals(2, countRowsInTable("prs_names"));
            assertEquals(2, countRowsInTable("prs_sor_persons"));
        }
    }

    // TODO: Note, all of these tests below should be updated to use a role and check its expiration date.

    // test delete SoR Person with it being a mistake (and only one SoR) (i.e. were the appropriate roles, and names removed? from the calculated person?)
    @Test
    @Rollback
    public void testDeleteSoRPersonAsAMistakeWithOnlyOneSoR() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);

        final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.addPerson(criteria);
        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult.getTargetObject().getId(), OR_WEBAPP_IDENTIFIER);
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        assertTrue(this.personService.deleteSystemOfRecordPerson(sorPerson, true, null));

        this.entityManager.flush();

        assertEquals(0, countRowsInTable("prs_sor_persons"));
        assertEquals(0, countRowsInTable("prs_names"));
        assertEquals(0, countRowsInTable("prc_persons"));
        assertEquals(0, countRowsInTable("prc_names"));
    }

    // test delete SoR Person with it being a mistake (and there being two SORs)
    @Test
    public void testDeleteSoRPersonAsMistakeWIthTwoSoRs() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.addPerson(criteria);
        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult.getTargetObject().getId(), OR_WEBAPP_IDENTIFIER);

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);

        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        this.entityManager.flush();
        
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(2, countRowsInTable("prs_sor_persons"));
        assertEquals(2, countRowsInTable("prs_names"));

        assertTrue(this.personService.deleteSystemOfRecordPerson(sorPerson1, true, null));

        this.entityManager.flush();

        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
    }

    // test delete SoR Person with no mistake (2 sors)
     @Test
     @Rollback
     public void testDeleteSoRPersonNoMistakeTwoSoRs() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), OR_WEBAPP_IDENTIFIER, null);
        final ServiceExecutionResult<Person> serviceExecutionResult = this.personService.addPerson(criteria);
        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult.getTargetObject().getId(), OR_WEBAPP_IDENTIFIER);

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        this.entityManager.flush();

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(2, countRowsInTable("prs_sor_persons"));
        assertEquals(2, countRowsInTable("prs_names"));

        assertTrue(this.personService.deleteSystemOfRecordPerson(sorPerson, false, "FIRED"));

        this.entityManager.flush();

        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
     }


    /**
     * Tests for the Add Role Use Case
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNoRolePassedIn() {
        this.personService.validateAndSaveRoleForSorPerson(new JpaSorPersonImpl(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoPersonPassedIn() {
        this.personService.validateAndSaveRoleForSorPerson(null, new JpaSorRoleImpl(this.referenceRepository.getTypeById(new Long(6)), new JpaSorPersonImpl()));
    }

    // UPDATE SOR ROLE
     @Test
     @Rollback
     public void testUpdateExistingSoRRole() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));


        final SorRole sorRole = sorPerson.createRole(this.referenceRepository.getTypeById(new Long(6)));
        sorRole.setSorId("1");
        sorRole.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        sorRole.setPercentage(50);
        sorRole.setStart(new Date());
        sorRole.setPersonStatus(this.referenceRepository.getTypeById(1L));
        sorRole.setSponsorId(1L);
        sorRole.setSponsorType(this.referenceRepository.getTypeById(1L));
        sorRole.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    sorRole.setTitle("Application Developer");
        sorPerson.addRole(sorRole);

        final EmailAddress emailAddress = sorRole.createEmailAddress();
        emailAddress.setAddress("scott@scott.com");
        emailAddress.setAddressType(this.referenceRepository.getTypeById(1L));
        sorRole.addEmailAddress(emailAddress);

        final Address address = sorRole.createAddress();
        address.setLine1("123 ABC");
        address.setCity("Any City");
        address.setPostalCode("12345");
        //dont need to set the region
        //address.setRegion(this.referenceRepository.getRegionByCodeOrName("NJ"));
        address.setCountry(this.referenceRepository.getCountryById(1L));
        address.setType(this.referenceRepository.getTypeById(1L));
        sorRole.addAddress(address);

        final ServiceExecutionResult<SorRole> serviceExecutionResult = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
        this.entityManager.flush();
        assertTrue(serviceExecutionResult.getValidationErrors().isEmpty());
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));
        assertEquals(1, countRowsInTable("prs_emails"));
        assertEquals(1, countRowsInTable("prc_emails"));
        assertEquals(1, countRowsInTable("prs_addresses"));
        assertEquals(1, countRowsInTable("prc_addresses"));

        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");
        final SorRole sorRole1 = sorPerson1.findSorRoleBySorRoleId(serviceExecutionResult.getTargetObject().getSorId());

        sorRole1.setPercentage(100);
        
        for (final EmailAddress sorEmailAddress: sorRole1.getEmailAddresses()) {
            sorEmailAddress.setAddress("scottie@scott.com");
        }

        for (final Address sorAddress: sorRole1.getAddresses()) {
            sorAddress.setLine1("456 DEF");
        }
        
        //this.personService.updateSorRole(sorPerson1, sorRole1);
        this.personService.updateSorPerson(sorPerson1);
        this.entityManager.flush();
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));
        assertEquals(1, countRowsInTable("prs_emails"));
        assertEquals(1, countRowsInTable("prc_emails"));
        assertEquals(1, countRowsInTable("prs_addresses"));
        assertEquals(1, countRowsInTable("prc_addresses"));

        final Person person = this.personService.findPersonById(serviceExecutionResult1.getTargetObject().getId());


        Role personRole = null;
        for (Role role: person.getRoles()) if (role.getSorRoleId().equals(sorRole1.getId())) personRole = role;

        assertEquals(100, personRole.getPercentage());
     }

    // UPDATE SOR ROLE
     @Test
     @Rollback
     public void testUpdateExistingSoRRoleAddFirstAddress() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);

        SorPerson sorPerson = criteria1.getSorPerson();
        final SorRole sorRole = sorPerson.createRole(this.referenceRepository.getTypeById(new Long(6)));

        sorRole.setSorId("1");
        sorRole.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        sorRole.setPercentage(50);
        sorRole.setStart(new Date());
        sorRole.setPersonStatus(this.referenceRepository.getTypeById(1L));
        sorRole.setSponsorId(1L);
        sorRole.setSponsorType(this.referenceRepository.getTypeById(1L));
        sorRole.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    sorRole.setTitle("Application Developer");
        sorPerson.addRole(sorRole);

        //add a person with one role
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        this.entityManager.flush();

        assertTrue(serviceExecutionResult1.getValidationErrors().isEmpty());
        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));

        sorPerson = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        assertNotNull(sorPerson);
        assertEquals(1, sorPerson.getRoles().size());
        final SorRole sorRole1 = sorPerson.getRoles().get(0);

        sorRole1.setSorId("1");
        sorRole1.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        sorRole1.setPercentage(100);
        sorRole1.setStart(new Date());
        sorRole1.setPersonStatus(this.referenceRepository.getTypeById(1L));
        sorRole1.setSponsorId(1L);
        sorRole1.setSponsorType(this.referenceRepository.getTypeById(1L));

        //add an address
        final Address address = sorRole1.createAddress();
        address.setLine1("123 ABC");
        address.setCity("Any City");
        address.setPostalCode("12345");
        address.setCountry(this.referenceRepository.getCountryById(1L));
        address.setType(this.referenceRepository.getTypeById(1L));
        sorRole1.addAddress(address);

        this.personService.updateSorPerson(sorPerson);
        this.entityManager.flush();
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));
        assertEquals(1, countRowsInTable("prs_addresses"));
        assertEquals(1, countRowsInTable("prc_addresses"));

        final Person person = this.personService.findPersonById(serviceExecutionResult1.getTargetObject().getId());
        //final Role role = person.getRoles().get(0);
        //final Address address1 = role.getAddresses().get(0);
        //assertEquals("Any City", address1.getCity());
     }


     // Test update SoR role with validation errors
     @Test 
     @Rollback
     public void testUpdateSoRRoleInvalidAddress () {
    	 MessageSource ms = (MessageSource)applicationContext.getBean("messageSource");
         final ReconciliationCriteria criteria = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
         SorPerson sorPerson = criteria.getSorPerson();

         SorRole sorRole = sorPerson.createRole(referenceRepository.findType(Type.DataTypes.AFFILIATION, Type.AffiliationTypes.FACULTY));
         sorRole.setSorId("1");
         sorRole.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
         sorRole.setPercentage(100);
         sorRole.setStart(new Date());
         sorRole.setPersonStatus(this.referenceRepository.getTypeById(1L));
         sorRole.setSponsorId(1L);
         sorRole.setSponsorType(this.referenceRepository.getTypeById(1L));
         sorRole.setTitle("Professor for testing");
         sorPerson.addRole(sorRole);
     	
         Address newAddress = sorRole.createAddress();
         newAddress.setType(referenceRepository.findType(Type.DataTypes.ADDRESS, Type.AddressTypes.OFFICE));
         newAddress.setCity("");
         newAddress.setPostalCode("12");
         sorRole.addAddress(newAddress);
         
         final ServiceExecutionResult<SorRole> serviceExecutionResult = this.personService.validateAndSaveRoleForSorPerson(sorPerson, sorRole);
         
         // check the result of adding an address
         assertFalse("Execution result when adding an invalid address must be unsuccessful", serviceExecutionResult.succeeded());
         assertFalse("Execution result when adding an invalid address must have a non-empty error set",serviceExecutionResult.getValidationErrors().isEmpty());
     }

    @Test
    @Rollback
    public void testAddRoleForSoRPerson() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final SorRole role = sorPerson1.createRole(this.referenceRepository.getTypeById(new Long(6)));
        role.setSorId("1");
        role.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        role.setPercentage(50);
        role.setStart(new Date());
        role.setPersonStatus(this.referenceRepository.getTypeById(1L));
        role.setSponsorId(1L);
        role.setSponsorType(this.referenceRepository.getTypeById(1L));
        role.setAffiliationType(this.referenceRepository.getTypeById(new Long(6)));
        role.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    role.setTitle("Application Developer");
        sorPerson1.addRole(role);

        final ServiceExecutionResult<SorRole> ser = this.personService.validateAndSaveRoleForSorPerson(sorPerson1, role);
        entityManager.flush();

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));

        final Person person = this.personService.findPersonById(serviceExecutionResult1.getTargetObject().getId());

        Role cRole = null;
        for (Role prole: person.getRoles()) cRole = prole;

        final SorRole sRole = ser.getTargetObject();
        assertEquals(sRole.getPercentage(), cRole.getPercentage());
        assertEquals(sRole.getStart(), cRole.getStart());
    }

    @Test
    @Rollback
    public void testAddRoleForSoRPersonWithNoRoleID() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);

        this.entityManager.flush();

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");
        assertNotNull(sorPerson1);

        final SorRole role = sorPerson1.createRole(this.referenceRepository.getTypeById(new Long(6)));
        role.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        role.setPercentage(50);
        role.setStart(new Date());
        role.setPersonStatus(this.referenceRepository.getTypeById(1L));
        role.setSponsorId(1L);
        role.setSponsorType(this.referenceRepository.getTypeById(1L));
        role.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    role.setTitle("Application Developer");
        sorPerson1.addRole(role);

        final ServiceExecutionResult<SorRole> ser = this.personService.validateAndSaveRoleForSorPerson(sorPerson1, role);
        entityManager.flush();

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));
        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));

        final Person person = this.personService.findPersonById(serviceExecutionResult1.getTargetObject().getId());

        Role cRole = null;
        for (Role prole: person.getRoles()) cRole = prole;
        final SorRole sRole = ser.getTargetObject();
        assertEquals(sRole.getPercentage(), cRole.getPercentage());
        assertEquals(sRole.getStart(), cRole.getStart());
    }


	/**
	 * Tests for Delete SorRole
	 */

	@Test
    @Rollback
    public void testDeleteSorRoleNoMistake() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final SorRole role = sorPerson1.createRole(this.referenceRepository.getTypeById(new Long(6)));
        role.setSorId("1");
        role.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        role.setPercentage(50);
        role.setStart(new Date());
        role.setPersonStatus(this.referenceRepository.getTypeById(1L));
        role.setSponsorId(1L);
        role.setSponsorType(this.referenceRepository.getTypeById(1L));
        role.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    role.setTitle("Application Developer");
        sorPerson1.addRole(role);

        final ServiceExecutionResult<SorRole> ser = this.personService.validateAndSaveRoleForSorPerson(sorPerson1, role);
        entityManager.flush();

        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));

		final boolean deleted = personService.deleteSystemOfRecordRole(sorPerson1, ser.getTargetObject(),false,Type.TerminationTypes.FIRED.name());
		assertTrue("Delete SorRole failed",deleted);
		entityManager.flush();

		assertEquals(0, countRowsInTable("prs_role_records"));
		assertEquals(1, countRowsInTable("prc_role_records"));
		final Long termination = this.simpleJdbcTemplate.queryForObject("select termination_t from prc_role_records where person_id = ?", Long.class, serviceExecutionResult1.getTargetObject().getId());

		assertEquals(4,termination.intValue());
    }

	@Test
    @Rollback
    public void testDeleteSorRoleWithMistake() throws ReconciliationException, SorPersonAlreadyExistsException {
        final ReconciliationCriteria criteria1 = constructReconciliationCriteria(RUDYARD, KIPLING, null, EMAIL_ADDRESS, PHONE_NUMBER, new Date(0), "FOO", null);
        final ServiceExecutionResult<Person> serviceExecutionResult1 = this.personService.addPerson(criteria1);
        final SorPerson sorPerson1 = this.personService.findByPersonIdAndSorIdentifier(serviceExecutionResult1.getTargetObject().getId(), "FOO");

        assertEquals(1, countRowsInTable("prc_persons"));
        assertEquals(1, countRowsInTable("prc_names"));
        assertEquals(1, countRowsInTable("prs_sor_persons"));
        assertEquals(1, countRowsInTable("prs_names"));

        final SorRole role = sorPerson1.createRole(this.referenceRepository.getTypeById(new Long(6)));
        role.setSorId("1");
        role.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
        role.setPercentage(50);
        role.setStart(new Date());
        role.setPersonStatus(this.referenceRepository.getTypeById(1L));
        role.setSponsorId(1L);
        role.setSponsorType(this.referenceRepository.getTypeById(1L));
        role.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
	    role.setTitle("Application Developer");
        sorPerson1.addRole(role);

        final ServiceExecutionResult<SorRole> ser = this.personService.validateAndSaveRoleForSorPerson(sorPerson1, role);
        entityManager.flush();

        assertEquals(1, countRowsInTable("prs_role_records"));
        assertEquals(1, countRowsInTable("prc_role_records"));

		final boolean deleted = personService.deleteSystemOfRecordRole(sorPerson1, ser.getTargetObject(),true,Type.TerminationTypes.FIRED.name());
		assertTrue("Delete SorRole failed",deleted);
		entityManager.flush();

		assertEquals(0, countRowsInTable("prs_role_records"));
		assertEquals(0, countRowsInTable("prc_role_records"));
    }
}