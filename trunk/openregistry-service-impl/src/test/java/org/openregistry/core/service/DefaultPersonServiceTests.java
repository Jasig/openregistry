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
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.MockReconciliationCriteria;
import org.openregistry.core.domain.sor.MockSorName;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.repository.MockPersonRepository;
import org.openregistry.core.repository.MockReferenceRepository;
import org.openregistry.core.repository.PersonRepository;
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

    private PersonRepository personRepository;

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
        MockSorName name = new MockSorName();
        name.setGiven("Sam");
        name.setFamily("Malone");
        name.setMiddle("B");
        name.setPrefix("");
        name.setSuffix("");
        SorPerson sorPerson = reconciliationCriteria.getPerson();
        sorPerson.addName(name);
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
     *
     * // TODO: this test is actually technically incorrect.
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