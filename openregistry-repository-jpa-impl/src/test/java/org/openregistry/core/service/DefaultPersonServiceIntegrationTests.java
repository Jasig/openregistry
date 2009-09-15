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

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.openregistry.core.repository.PersonRepository;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.domain.jpa.sor.*;
import org.openregistry.core.domain.jpa.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;
import org.openregistry.core.service.reconciliation.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.apache.log4j.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Integration test for {@link DefaultPersonService} that links up with the JPA
 * repositories.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */

@ContextConfiguration(locations = {"classpath:test-personServices-context.xml"})
public final class DefaultPersonServiceIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

	private final String OR_WEBAPP_IDENTIFIER = "or-webapp";
	private final String REGISTRAR_IDENTIFIER = "registrar";


//    private static final String IDENTIFIER_TYPE ="NetId";

//    private static final String IDENTIFIER_VALUE ="test";

//    private static final String LOCK_VALUE = "LOCK";

	private static final String EMAIL_ADDRESS = "test@test.edu";
	private static final String PHONE_NUMBER = "555-555-5555";
	private static final String RUDYARD = "Rudyard";
	private static final String KIPLING = "Kipling";
	private static final String RUDY = "Rudy";
	private static final String KIPSTEIN = "Kipstein";


	private static Logger logger = Logger.getLogger(DefaultPersonServiceIntegrationTests.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ReferenceRepository referenceRepository;

    @PersistenceContext
    private EntityManager entityManager;

	private  Name name1, name2, name3;
	private ReconciliationCriteria reconciliationCriteria1, reconciliationCriteria2, reconciliationCriteria3;

	@Before
    public void setUp() throws Exception {
		reconciliationCriteria1 = new JpaReconciliationCriteriaImpl();
		reconciliationCriteria1.setEmailAddress(EMAIL_ADDRESS);
		reconciliationCriteria1.setPhoneNumber(PHONE_NUMBER);
		SorPerson sorPerson1 = reconciliationCriteria1.getPerson();
		name1 = sorPerson1.addName();
		name1.setGiven(RUDYARD);
		name1.setFamily(KIPLING);
		sorPerson1.setSourceSorIdentifier(OR_WEBAPP_IDENTIFIER);
		sorPerson1.setDateOfBirth(new Date(0));
		sorPerson1.setGender("M");

		reconciliationCriteria2 = new JpaReconciliationCriteriaImpl();
		reconciliationCriteria2.setEmailAddress(EMAIL_ADDRESS);
		reconciliationCriteria2.setPhoneNumber(PHONE_NUMBER);
		SorPerson sorPerson2 = reconciliationCriteria2.getPerson();
		name2 = sorPerson2.addName();
		name2.setGiven(RUDYARD);
		name2.setFamily(KIPLING);
		sorPerson2.setSourceSorIdentifier(REGISTRAR_IDENTIFIER);
		sorPerson2.setDateOfBirth(new Date(0));
		sorPerson2.setGender("M");

		reconciliationCriteria3 = new JpaReconciliationCriteriaImpl();
		reconciliationCriteria3.setEmailAddress(EMAIL_ADDRESS);
		reconciliationCriteria3.setPhoneNumber(PHONE_NUMBER);
		SorPerson sorPerson3 = reconciliationCriteria3.getPerson();
		name3 = sorPerson3.addName();
		name3.setGiven(RUDY);
		name3.setFamily(KIPSTEIN);
		sorPerson3.setSourceSorIdentifier(OR_WEBAPP_IDENTIFIER);
		sorPerson3.setDateOfBirth(new Date(0));
		sorPerson3.setGender("M");

    }

    @After
    public void tearDown() throws Exception {
		this.reconciliationCriteria1 = null;
		this.reconciliationCriteria2 = null;
		this.reconciliationCriteria3 = null;

    }

	@Test
	public void testAddPerson(){
		ServiceExecutionResult result = personService.addPerson(reconciliationCriteria1,null);
		assertTrue(result.succeeded());
		assertEquals(1,countRowsInTable("prc_persons"));
		assertEquals(1,countRowsInTable("prc_names"));
		assertTrue("personService.addPerson didn't return a ReconciliationServiceExecutionResult",result instanceof ReconciliationServiceExecutionResult);
		assertTrue("JpaPersonImpl not created properly",result.getTargetObject() instanceof JpaPersonImpl);
		Person person = (JpaPersonImpl)result.getTargetObject();
		assertEquals("names do not match",name1.getFormattedName(),person.getPreferredName().getFormattedName());
	}

	/**
	 * adds one person, then adds a second with identical values and verifies that it doesn't add an additional person
	 */
	@Test
	public void testAddIdenticalPerson(){
		ServiceExecutionResult result1 = personService.addPerson(reconciliationCriteria1,null);
		assertEquals(1,countRowsInTable("prc_persons"));
		assertEquals(1,countRowsInTable("prc_names"));
		assertEquals(1,countRowsInTable("prs_sor_persons"));
		assertEquals(1,countRowsInTable("prs_names"));

		ServiceExecutionResult result2 = personService.addPerson(reconciliationCriteria2,null);
		assertTrue(result2.succeeded());
		assertEquals(ReconciliationResult.ReconciliationType.EXACT,	result2.getReconciliationResult().getReconciliationType());
		assertEquals(1,countRowsInTable("prc_persons"));
		assertEquals(1,countRowsInTable("prc_names"));
		assertTrue("personService.addPerson didn't return a ReconciliationServiceExecutionResult",result2 instanceof ReconciliationServiceExecutionResult);
		assertTrue("JpaPersonImpl not created properly",result2.getTargetObject() instanceof JpaPersonImpl);
		Person person = (JpaPersonImpl)result2.getTargetObject();
		assertEquals("names do not match",name2.getFormattedName(),person.getPreferredName().getFormattedName());

	}

	/**
	 * adds one person, then adds a second with different values, verifies an inexact match and adds the second person, then verifies that two entries exist
	 */
	@Test
	public void testAddSecondPerson(){
		ServiceExecutionResult result1 = personService.addPerson(reconciliationCriteria1,null);
		assertEquals(1,countRowsInTable("prc_persons"));
		assertEquals(1,countRowsInTable("prc_names"));

		ServiceExecutionResult result2 = personService.addPerson(reconciliationCriteria3,null);
		assertNotNull("ReconciliationResult is null", result2.getReconciliationResult());
		assertEquals(ReconciliationResult.ReconciliationType.NONE,	result2.getReconciliationResult().getReconciliationType());

		ServiceExecutionResult result3 = personService.addPerson(reconciliationCriteria3,result2.getReconciliationResult());
		assertTrue(result3.succeeded());
		assertNull(result3.getReconciliationResult());
		assertEquals(2,countRowsInTable("prc_persons"));
		assertEquals(2,countRowsInTable("prc_names"));
		assertTrue("personService.addPerson didn't return a ReconciliationServiceExecutionResult",result3 instanceof ReconciliationServiceExecutionResult);
		assertTrue("JpaPersonImpl not created properly",result3.getTargetObject() instanceof JpaPersonImpl);
		Person person = (JpaPersonImpl)result3.getTargetObject();
		assertEquals("names do not match",name3.getFormattedName(),person.getPreferredName().getFormattedName());

	}



}