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

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.repository.*;
import org.openregistry.core.service.identifier.NoOpIdentifierGenerator;
import org.openregistry.core.service.reconciliation.MockReconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult.*;
import org.openregistry.core.service.reconciliation.ReconciliationException;
import org.springframework.beans.factory.ObjectFactory;

import java.util.Date;

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
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.NONE));
        this.personService.setPersonObjectFactory(this.objectFactory);
        reconciliationCriteria = new MockReconciliationCriteria();
        setReconciliationCriteria(reconciliationCriteria);
    }

    void setReconciliationCriteria(ReconciliationCriteria reconciliationCriteria){
        SorPerson sorPerson = reconciliationCriteria.getPerson();
        final MockSorName name = (MockSorName) sorPerson.addName(referenceRepository.findType(Type.DataTypes.NAME, "Formal"));
        name.setGiven("Sam");
        name.setFamily("Malone");
        name.setMiddle("B");
        name.setPrefix("");
        name.setSuffix("");
        sorPerson.setGender("Male");
        sorPerson.setDateOfBirth(new Date());
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
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.NONE));
        this.personService.setPersonObjectFactory(this.objectFactory);
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
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.EXACT));
        this.personService.setPersonObjectFactory(this.objectFactory);
        ServiceExecutionResult<Person> result = this.personService.addPerson(reconciliationCriteria);
        assertNotNull(result);
        assertNotNull(result.getTargetObject());
    }

     /**
     * Tests if reconciliation result is MAYBE that a person is not returned.
     */
    @Test(expected = ReconciliationException.class)
    public void testReconciliationResultMaybeMatch() throws ReconciliationException {
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
         this.personService.setPersonObjectFactory(this.objectFactory);
        this.personService.addPerson(reconciliationCriteria);
     }

     /**
     * Tests if old reconciliationResult provided that person is returned, activation key is returned, identifiers created.
     */
    @Test
    public void testReconciliationResultOldReconciliationResultProvided() {
         this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
         this.personService.setPersonObjectFactory(this.objectFactory);

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

        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
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

        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
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

        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, true, null);


		Person person = personRepository.findByIdentifier("NetId","testId");
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
        this.personService = new DefaultPersonService(personRepository, this.referenceRepository, new NoOpIdentifierGenerator(), new MockReconciler(ReconciliationType.MAYBE));
        this.personService.deleteSystemOfRecordPerson(sorPerson, true, null);
        assertNull(personRepository.findByInternalId(1L));
	    personRepository.findByIdentifier("NetId","testId");
    }



    //TODO need to add test cases for conditionally required fields.

    //TODO field level test cases need to be added to the integration tests since annotations are not available in Mocked classes at the api level.

    /** TODO need to add this to integration tests
     * Tests that reconciliationCriteria must provide Source Sor Id.

    @Test
    public void testSourceSorIdRequired() {
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        Assert.notEmpty(result.getValidationErrors());
    }
   */

}