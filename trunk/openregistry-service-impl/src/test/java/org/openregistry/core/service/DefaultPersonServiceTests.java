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
package org.openregistry.core.service;

import static org.junit.Assert.*;
import org.junit.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.identifier.*;
import org.openregistry.core.service.reconciliation.*;
import org.openregistry.core.service.reconciliation.ReconciliationResult.*;
import org.springframework.beans.factory.*;

import java.util.*;

/**
 * Test cases for the {@link DefaultIdentifierChangeService}.  Note this does not actually
 * test the database.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class DefaultPersonServiceTests {

    private DefaultPersonService personService;

    private ObjectFactory<Person> objectFactory;

    private MockPersonRepository personRepository;

    private MockReferenceRepository referenceRepository = new MockReferenceRepository();

    private ReconciliationCriteria reconciliationCriteria;

    @Before
    public void setUp() throws Exception {
        this.personRepository = new MockPersonRepository(new MockPerson());
        this.objectFactory = new ObjectFactory<Person>() {
            public Person getObject() {
                return new MockPerson();
            }
        };
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.NONE));
        reconciliationCriteria = new MockReconciliationCriteria();
        setReconciliationCriteria(reconciliationCriteria);
    }

    void setReconciliationCriteria(ReconciliationCriteria reconciliationCriteria){
        SorPerson sorPerson = reconciliationCriteria.getSorPerson();
        final MockSorName name = (MockSorName) sorPerson.addName(referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setGiven("Sam");
        name.setFamily("Malone");
        name.setMiddle("B");
        name.setPrefix("");
        name.setSuffix("");
        sorPerson.setGender("Male");
        sorPerson.setDateOfBirth(new Date());
        reconciliationCriteria.setEmailAddress("test@test.com");
    }

     /**
     * Tests the illegal argument exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalArgument() throws ReconciliationException {
        this.personService.addPerson(null);
    }

    /**
     * Tests if reconciliation result is NONE that person is returned, activation key is returned, identifiers created.
     */
    @Test
    public void testReconciliationResultNoneReturnsPerson() throws ReconciliationException {
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.NONE));
        ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);
        assertNotNull(result);
        assertNotNull(result.getTargetObject());
        assertNotNull((result.getTargetObject()).getCurrentActivationKey());
        assertFalse((result.getTargetObject()).getIdentifiers().isEmpty());
    }

    /**
     * Tests if reconciliation result is EXACT that person is returned.
     */
    @Test
    public void testReconciliationResultExactMatch() throws ReconciliationException {
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.EXACT));
        ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);
        assertNotNull(result);
        assertNotNull(result.getTargetObject());
    }

     /**
     * Tests if reconciliation result is MAYBE that a person is not returned.
     */
    @Test(expected = ReconciliationException.class)
    public void testReconciliationResultMaybeMatch() throws ReconciliationException {
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.addPerson(reconciliationCriteria);
     }

    @Test(expected=IllegalStateException.class)
    public void testAddPersonAndLinkWithBadCriteria() {
        this.personService.addPersonAndLink(new MockReconciliationCriteria(), new MockPerson());
    }

    @Test(expected=IllegalStateException.class)
    public void testAddPersonAndLinkWithBadPerson() {
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));

        try {
            this.personService.addPerson(reconciliationCriteria);
        } catch (ReconciliationException e) {
            final Person p = e.getMatches().get(0).getPerson();
            final MockPerson m = new MockPerson();
            m.setId(5L);
            final ServiceExecutionResult<Person> ser = this.personService.addPersonAndLink(reconciliationCriteria, m);
        }
    }

    @Test
    public void testAddPersonAndLink() {
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));

        try {
            this.personService.addPerson(reconciliationCriteria);
        } catch (ReconciliationException e) {
            final Person p = e.getMatches().get(0).getPerson();
            final ServiceExecutionResult<Person> ser = this.personService.addPersonAndLink(reconciliationCriteria, p);

            assertTrue(ser.succeeded());
            assertEquals(p.getId(), ser.getTargetObject().getId());
        }
    }

     /**
     * Tests if old reconciliationResult provided that person is returned, activation key is returned, identifiers created.
     */
    @Test
    public void testReconciliationResultOldReconciliationResultProvided() {
         this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));

         try {
            this.personService.addPerson(reconciliationCriteria);
         } catch (final ReconciliationException ex) {
            final ServiceExecutionResult<Person> result = this.personService.forceAddPerson(reconciliationCriteria);
             assertNotNull(result.getTargetObject().getCurrentActivationKey());
             assertFalse(result.getTargetObject().getIdentifiers().isEmpty());

         }
    }

    /*
     * DELETE SOR PERSON TESTS
     */


    // test delete SoR Person with no mistake (2 sors)
    @Test
    public void testDeleteSoRPersonNoMistakeTwoSoRs() throws ReconciliationException {
        final MockPerson mockPerson = new MockPerson();

		final MockSorPerson sorPerson = new MockSorPerson();
		sorPerson.setPersonId(1L);
        sorPerson.setId(1L);
		final MockSorRole sorRole = (MockSorRole) sorPerson.addRole((RoleInfo) null);
		sorRole.setId(1L);
        sorRole.setSorId("500");

        mockPerson.addRole(sorRole);

		final MockSorPerson sorPerson1 = new MockSorPerson();
        final MockSorRole sorRole1 = (MockSorRole) sorPerson1.addRole((RoleInfo) null);
		sorPerson1.setPersonId(1L);
        sorPerson1.setId(2L);
        sorRole1.setId(2L);
        sorRole1.setSorId("600");

        mockPerson.addRole(sorRole1);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson, sorPerson1});

        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, false, null);

		final Person person = personRepository.findByInternalId(1L);
		assertNotNull(person);

		final Type terminationReason = person.getRoles().get(0).getTerminationReason();

		// verify that since there was no mistake, the role remains but a termination reason is set
		assertEquals(terminationReason.getDataType(),Type.DataTypes.TERMINATION.name());
		assertEquals(terminationReason.getDescription(),Type.TerminationTypes.UNSPECIFIED.name());

		// verify that the second role does not have a termination reason
		assertNull(person.getRoles().get(1).getTerminationReason());

		// verify that the sorPerson is gone
		assertNull(personRepository.findSorByInternalId(1L));
    }

    // test delete SoR Person no mistake
    @Test
    public void testDeleteSoRPersonNoMistakeOneSoR() throws ReconciliationException {
        final MockPerson mockPerson = new MockPerson();

        final SorPerson sorPerson = new MockSorPerson();
        sorPerson.setPersonId(1L);
		MockSorRole sorRole = new MockSorRole(1L);
		sorPerson.addRole(sorRole);

		MockRole mockRole = new MockRole(1L);
		mockRole.setSorRoleId(sorRole.getId());
		mockPerson.addRole(mockRole);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson});

        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, false, null);

		Person person = personRepository.findByInternalId(1L);

		assertNotNull(person);

		Type terminationReason = person.getRoles().get(0).getTerminationReason();

		// verify that since there was no mistake, the role remains but a termination reason is set
		assertEquals(terminationReason.getDataType(),Type.DataTypes.TERMINATION.name());
		assertEquals(terminationReason.getDescription(),Type.TerminationTypes.UNSPECIFIED.name());

		// verify that the sorPerson is gone
		assertNull(personRepository.findSorByInternalId(1L));
    }

    // test delete SoR Person with it being a mistake (and there being two SORs)
    @Test
    public void testDeleteSoRPersonAsMistakeWIthTwoSoRs() throws ReconciliationException {
        final MockPerson mockPerson = new MockPerson();

        final SorPerson sorPerson = new MockSorPerson();
        sorPerson.setPersonId(1L);
		MockSorRole sorRole = new MockSorRole(1L);
		sorPerson.addRole(sorRole);

		MockRole mockRole = new MockRole(1L);
		mockRole.setSorRoleId(sorRole.getId());
		mockPerson.addRole(mockRole);


        final SorPerson sorPerson1 = new MockSorPerson();
        sorPerson1.setPersonId(1L);
		MockSorRole sorRole1 = new MockSorRole(2L);
		sorPerson1.addRole(sorRole1);

		MockRole mockRole1 = new MockRole(2L);
		mockRole1.setSorRoleId(sorRole1.getId());
		mockPerson.addRole(mockRole1);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson, sorPerson1});

        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, true, null);


		Person person = personRepository.findByIdentifier("NETID","testId");
		assertNotNull(person);
		assertEquals("Not just one role left",person.getRoles().size(),1);
		assertEquals(person.getRoles().get(0).getId(),new Long(2));
    }

    // test delete SoR Person with it being a mistake (and only one SoR) (i.e. were the appropriate roles, and names removed? from the calculated person?)
    @Test(expected=RepositoryAccessException.class)
    public void testDeleteSoRPersonAsAMistakeWithOnlyOneSoR() throws ReconciliationException {
        final SorPerson sorPerson = new MockSorPerson();
        sorPerson.setPersonId(1L);
        final MockSorRole sorRole = (MockSorRole) sorPerson.addRole((RoleInfo) null);
        sorRole.setSorId("1");
        sorRole.setId(2L);

		final MockPerson mockPerson = new MockPerson();
        mockPerson.addRole(sorRole);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson});
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, this.referenceRepository, new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, true, null);
        assertNull(personRepository.findByInternalId(1L));
	    personRepository.findByIdentifier("NETID","testId");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddRoleWithNoParameters() {
        this.personService.validateAndSaveRoleForSorPerson(null, null);
    }

    @Test
    public void testAddNewRoleForPerson() {
        final MockSorPerson mockSorPerson = new MockSorPerson();
        mockSorPerson.setSourceSor("FOO");
        mockSorPerson.setPersonId(1L);

        final MockSorRole sorRole = (MockSorRole) mockSorPerson.addRole((RoleInfo) null);
        sorRole.setSorId("1");
        sorRole.setSourceSorIdentifier("FOO");
        sorRole.setId(2L);

        final MockPerson mockPerson = new MockPerson();
        mockPerson.setId(1L);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {mockSorPerson});
        this.personService = new DefaultPersonService(this.objectFactory, personRepository, this.referenceRepository, new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        final ServiceExecutionResult<SorRole> serviceExecutionResult = this.personService.validateAndSaveRoleForSorPerson(mockSorPerson, sorRole);

        assertTrue(serviceExecutionResult.succeeded());

        final Person person = personRepository.findByInternalId(1L);
        assertEquals(1, person.getRoles().size());
    }

    //TODO need to add test cases for conditionally required fields.

	/**
	 * DELETE SOR ROLE TESTS
	 */

	    // test delete SoR Role no mistake
    @Test
    public void testDeleteSoRRoleNoMistakeOneSoR() throws ReconciliationException {
        final MockPerson mockPerson = new MockPerson();

        final SorPerson sorPerson = new MockSorPerson();
        sorPerson.setPersonId(1L);
		sorPerson.setSourceSor("or-webapp");
		MockSorRole sorRole = new MockSorRole(1L);
		sorPerson.addRole(sorRole);

		MockRole mockRole = new MockRole(1L);
		mockRole.setSorRoleId(sorRole.getId());
		mockPerson.addRole(mockRole);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson});

        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));

		this.personService.deleteSystemOfRecordRole(sorPerson, sorRole, false, Type.TerminationTypes.FIRED.name());

		Person person = personRepository.findByInternalId(1L);
		assertNotNull(person);

		SorPerson fetchedSorPerson = personRepository.findByPersonIdAndSorIdentifier(1L,"or-webapp");

		// verify that the sorPerson no longer has any roles
		assertEquals(0,fetchedSorPerson.getRoles().size());

		Type terminationReason = person.getRoles().get(0).getTerminationReason();

		// verify that since there was no mistake, the role remains but a termination reason is set
		assertEquals(terminationReason.getDataType(),Type.DataTypes.TERMINATION.name());
		assertEquals(terminationReason.getDescription(),Type.TerminationTypes.FIRED.name());
    }

	    // test delete SoR Role with mistake
    @Test
    public void testDeleteSoRRoleWithMistakeOneSoR() throws ReconciliationException {
        final MockPerson mockPerson = new MockPerson();

        final SorPerson sorPerson = new MockSorPerson();
        sorPerson.setPersonId(1L);
		sorPerson.setSourceSor("or-webapp");
		MockSorRole sorRole = new MockSorRole(1L);
		sorPerson.addRole(sorRole);

		MockRole mockRole = new MockRole(1L);
		mockRole.setSorRoleId(sorRole.getId());
		mockPerson.addRole(mockRole);

        final MockPersonRepository personRepository = new MockPersonRepository(new Person[] {mockPerson}, new SorPerson[] {sorPerson});

        this.personService = new DefaultPersonService(this.objectFactory, personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));

		this.personService.deleteSystemOfRecordRole(sorPerson, sorRole, true, Type.TerminationTypes.FIRED.name());

		Person person = personRepository.findByInternalId(1L);
		assertNotNull(person);

		SorPerson fetchedSorPerson = personRepository.findByPersonIdAndSorIdentifier(1L,"or-webapp");

		// verify that the sorPerson no longer has any roles
		assertEquals(0,fetchedSorPerson.getRoles().size());

		// verify that the person also has no roles
		assertEquals(0,person.getRoles().size());

    }

}