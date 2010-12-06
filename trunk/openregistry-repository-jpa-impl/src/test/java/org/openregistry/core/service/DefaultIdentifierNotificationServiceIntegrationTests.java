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

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.sor.JpaReconciliationCriteriaImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorPersonImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:test-notificationServices-context.xml"})
public class DefaultIdentifierNotificationServiceIntegrationTests extends
		AbstractIntegrationTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private IdentifierNotificationService identifierNotificationService;
    
    @Inject
    private PersonService personService;
    
    @Inject
    private ReferenceRepository referenceRepository;
    
    private IdentifierType netIdIdentifierType;
    private Type homeEmailType;
    private final String NET_ID_VALUE = "testnetid";
    
    @Before
    public void setUp() {
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(998, 'STATUS', 'ACTIVE')");
        this.simpleJdbcTemplate.update("insert into ctx_data_types (id, data_type, description) values(999, 'EMAIL', 'HOME')");
    	homeEmailType = this.referenceRepository.getTypeById(new Long(999));
    	netIdIdentifierType = this.referenceRepository.findIdentifierType("NETID");
    }
   
    private Person constructPersonWithNetId
    	(final String firstName, final String lastName, final String emailAddress, final String netId) {
       
        final ReconciliationCriteria reconciliationCriteria = new JpaReconciliationCriteriaImpl();

        final JpaSorPersonImpl sorPerson = (JpaSorPersonImpl) reconciliationCriteria.getSorPerson();
        sorPerson.setDateOfBirth(new Date(0));
        sorPerson.setGender("M");
        sorPerson.setSourceSor("TEST_SOR");
	
        final SorName name = sorPerson.addName(this.referenceRepository.findType(Type.DataTypes.NAME, Type.NameTypes.FORMAL));
        name.setFamily(lastName);
        name.setGiven(firstName);       
       
        Person person = null;
		try {
			person = this.personService.addPerson(reconciliationCriteria).getTargetObject();
	        
			// add a Net ID
			person.addIdentifier(netIdIdentifierType, netId);
	        
			// set preferred email address
			person.getPreferredContactEmailAddress().setAddress(emailAddress);
			
	        // add role
			JpaSorRoleImpl sorRole = new JpaSorRoleImpl(this.referenceRepository.getTypeById(new Long(6)), sorPerson);
            sorRole.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(1)));
            sorRole.setSystemOfRecord(this.referenceRepository.findSystemOfRecord("test"));
            sorRole.setTitle("FOO");
			sorRole.setStart(new Date(0));
			sorRole.setPersonStatus(this.referenceRepository.getTypeById(new Long(998)));
			sorRole.setSponsorType(this.referenceRepository.getTypeById(new Long(5))); // see AbstractIntegrationTests.java
			sorRole.setSponsorId(new Long(0));
			sorRole.addOrUpdateEmail(emailAddress, homeEmailType);
			person.addRole(sorRole);

		} catch (Throwable e) {
			e.printStackTrace();
			fail("No exception should be thrown, caught "+e.getClass().getName()+" with message: "+e.getMessage());
		}
                
        return person;
    }
    
    @Test
    @Rollback
    public void testSuccessfullyNotifyUserWithoutRole() {
    	
    	Person person = this.constructPersonWithNetId("Firstname", "LastName", "test@test.test", NET_ID_VALUE);
    	assertNull("The notified date on Net ID must be null before notification", person.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());

    	this.identifierNotificationService.sendAccountActivationNotification(person, netIdIdentifierType);
    	
    	Person notifiedPerson = this.personService.findPersonByIdentifier(netIdIdentifierType.getName(), NET_ID_VALUE);
    	assertNotNull("The notified date on Net ID must not be null after successful notification", notifiedPerson.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());

    }
    
    @Test
    @Rollback
    public void testSuccessfullyNotifyUserWithRole() {
    	
    	Person person = this.constructPersonWithNetId("Firstname", "LastName", "test@test.test", NET_ID_VALUE);

    	assertNull("The notified date on Net ID must be null before notification", person.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());

    	this.identifierNotificationService.sendAccountActivationNotification(person, netIdIdentifierType, person.getRoles().get(0), homeEmailType);
    	
    	Person notifiedPerson = this.personService.findPersonByIdentifier(netIdIdentifierType.getName(), NET_ID_VALUE);
    	assertNotNull("The notified date on Net ID must not be null after successful notification", notifiedPerson.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());
     }
    
    @Test
    @Rollback
    public void testSuccessfullyNotifyUserWithTwoRoles() {
    	
    	Person person = this.constructPersonWithNetId("Firstname", "LastName", "test@test.test", NET_ID_VALUE);
		
    	// add a second role
    	final ReconciliationCriteria reconciliationCriteria = new JpaReconciliationCriteriaImpl();
    	final JpaSorPersonImpl sorPerson = (JpaSorPersonImpl) reconciliationCriteria.getSorPerson();
        sorPerson.setDateOfBirth(new Date(0));
        sorPerson.setGender("M");
        sorPerson.setSourceSor("TEST_SOR_2");
       
		JpaSorRoleImpl sorRole = new JpaSorRoleImpl(this.referenceRepository.getTypeById(new Long(7)), sorPerson);
		sorRole.setStart(new Date(0));
        sorRole.setTitle("FOO");
        sorRole.setOrganizationalUnit(this.referenceRepository.getOrganizationalUnitById(new Long(2)));
		sorRole.setPersonStatus(this.referenceRepository.getTypeById(new Long(998)));
		sorRole.setSponsorType(this.referenceRepository.getTypeById(new Long(5))); // see AbstractIntegrationTests.java
		sorRole.setSponsorId(new Long(0));
		sorRole.addOrUpdateEmail("test@test.test", homeEmailType);
		person.addRole(sorRole);

    	assertNull("The notified date on Net ID must be null before notification", person.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());

    	this.identifierNotificationService.sendAccountActivationNotification(person, netIdIdentifierType, person.getRoles().get(0), homeEmailType);

    	Date notificationDate = person.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate();
    	assertNotNull("The notified date on Net ID must not be null after the first notification", notificationDate);

    	try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	//this.identifierNotificationService.sendAccountActivationNotification(person, netIdIdentifierType, person.getRoles().get(1), homeEmailType);
    	
    	Date secondNotificationDate = person.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate();
    	assertNotNull("The notified date on Net ID must not be null after the second notification", secondNotificationDate);
    	
    	assertEquals("Notification date must not change after second notification", notificationDate.getTime(), secondNotificationDate.getTime());
    	
    	Person notifiedPerson = this.personService.findPersonByIdentifier(netIdIdentifierType.getName(), NET_ID_VALUE);
    	assertNotNull("The notified date on Net ID must not be null after successful notification", notifiedPerson.getIdentifiersByType().get(netIdIdentifierType.getName()).getFirst().getNotificationDate());
     }
}
