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

    private Person person;

    private ReconciliationCriteria reconciliationCriteria;

    private PersonRepository personRepository;

    private ReferenceRepository referenceRepository;

    private ObjectFactory objectFactory;

    private Reconciler reconciler;

    @Before
    public void setUp() throws Exception {
        this.person = new MockPerson();
        this.objectFactory = new ObjectFactory(){ public Person getObject() { return new MockPerson();}};
        this.referenceRepository = new MockReferenceRepository();
        this.personRepository = new MockPersonRepository(this.person);
        this.reconciler = new MockReconciler();
        this.personService = new DefaultPersonService(new MockPersonRepository(this.person), new MockReferenceRepository(), new MockActivationService(personRepository), new NoOpIdentifierGenerator(), objectFactory, reconciler);
        this.reconciliationCriteria = new MockReconciliationCriteria();
        MockSorName name = new MockSorName();
        name.setGiven("Sam");
        name.setFamily("Malone");
        SorPerson sorPerson = reconciliationCriteria.getPerson();
        sorPerson.addName(name);
        sorPerson.setGender("Male");
        sorPerson.setDateOfBirth(new Date());

    }

    /**
     * Tests the illegal argument exception.
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

}