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
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.identifier.NoOpIdentifierGenerator;
import org.openregistry.core.service.reconciliation.MockReconciler;
import org.openregistry.core.service.reconciliation.Reconciler;
import org.openregistry.core.service.reconciliation.ReconciliationResult;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.BeansException;

import java.util.Date;
import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 * Test cases for the {@link DefaultIdentifierChangeService}.  Note this does not actually
 * test the database.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class DefaultPersonServiceTests {

    private DefaultPersonService personService;

    private ObjectFactory objectFactory;

    private PersonRepository personRepository;

    private ReconciliationCriteria reconciliationCriteria;

    private final String MATCH_TYPE_NONE = "NONE";
    private final String MATCH_TYPE_EXACT = "EXACT";
    private final String MATCH_TYPE_MAYBE = "MAYBE";

    @Before
    public void setUp() throws Exception {
        this.personRepository = new MockPersonRepository(new MockPerson());
        this.objectFactory = new ObjectFactory(){ public Person getObject() { return new MockPerson();}};
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, new MockReconciler(MATCH_TYPE_NONE));
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
     * Tests passing null OldReconciliationResult.
     */
    @Test
    public void testNullOldReconciliationResult() {
        this.personService.addPerson(reconciliationCriteria, null);
    }

     /**
     * Tests the illegal argument exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalArgument() {
        this.personService.addPerson(null, null);
    }

    /**
     * Tests get a reconciliation result.
     */
    @Test
    public void testReconciliationResult() {
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        assertNotNull(result);
        ReconciliationResult recResult = result.getReconciliationResult();
        assertNotNull(recResult);
    }

    /**
     * Tests if reconciliation result is NONE that person is returned, activation key is returned, identifiers created.
     */
    @Test
    public void testReconciliationResultNoneReturnsPerson() {
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, new MockReconciler(MATCH_TYPE_NONE));
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        assertNotNull(result);
        ReconciliationResult recResult = result.getReconciliationResult();
        assertNotNull(recResult);
        assertNotNull(result.getTargetObject());
        assertSame(result.getTargetObject().getClass(),org.openregistry.core.domain.MockPerson.class);
        assertNotNull(((Person)result.getTargetObject()).getCurrentActivationKey());
        assertFalse(((Person)result.getTargetObject()).getIdentifiers().isEmpty());
    }

    /**
     * Tests if reconciliation result is EXACT that person is returned.
     */
    @Test
    public void testReconciliationResultExactMatch() {
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, new MockReconciler(MATCH_TYPE_EXACT));
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        assertNotNull(result);
        assertNotNull(result.getReconciliationResult());
        assertNotNull(result.getTargetObject());
        assertSame(result.getTargetObject().getClass(),org.openregistry.core.domain.MockPerson.class);
    }

     /**
     * Tests if reconciliation result is MAYBE that a person is not returned.
     */
    @Test
    public void testReconciliationResultMaybeMatch() {
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, new MockReconciler(MATCH_TYPE_MAYBE));
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        assertNotNull(result);
        assertNotNull(result.getReconciliationResult());
        assertNotNull(result.getTargetObject());
        assertSame(result.getTargetObject().getClass(),org.openregistry.core.domain.sor.MockReconciliationCriteria.class);
     }

     /**
     * Tests if old reconciliationResult provided that person is returned, activation key is returned, identifiers created.
     */
    @Test
    public void testReconciliationResultOldReconciliationResultProvided() {
        this.personService = new DefaultPersonService(personRepository, new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, new MockReconciler(MATCH_TYPE_MAYBE));
        ServiceExecutionResult result = this.personService.addPerson(reconciliationCriteria, null);
        assertNotNull(result);
        assertNotNull(result.getReconciliationResult());
        result = this.personService.addPerson(reconciliationCriteria, result.getReconciliationResult());
        //check person was added.
        assertNotNull(result.getTargetObject());
        assertSame(result.getTargetObject().getClass(),org.openregistry.core.domain.MockPerson.class);
        assertNotNull(((Person)result.getTargetObject()).getCurrentActivationKey());
        assertFalse(((Person)result.getTargetObject()).getIdentifiers().isEmpty());
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