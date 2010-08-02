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

package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.repository.ReferenceRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Deque;

import javax.inject.Inject;

/**
 * Tests for the {@link org.openregistry.core.domain.jpa.JpaPersonImpl}.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@ContextConfiguration(locations = {"classpath:base-integration-tests.xml"})
public class JpaPersonImplTests extends AbstractTransactionalJUnit4SpringContextTests {
   
	@Inject
    private ReferenceRepository referenceRepository;

    private static final String NOTIFIABLE_IDENTIFIER_NAME ="NID";
    private static final String NON_NOTIFIABLE_IDENTIFIER_NAME ="ID";
    private static final String NOTIFIABLE_IDENTIFIER_VALUE ="notifiable_test";
    private static final String NON_NOTIFIABLE_IDENTIFIER_VALUE ="non_notifiable_test";
    
    @Before
    public void createIdentifierTypes() throws Exception {
        this.simpleJdbcTemplate.update("insert into prd_identifier_types(identifier_t, name, format, private, modifiable, deleted, notifiable, description) values(null, '"+NOTIFIABLE_IDENTIFIER_NAME+"', 'format', false, false, false, true, '"+NOTIFIABLE_IDENTIFIER_NAME+"')");
        this.simpleJdbcTemplate.update("insert into prd_identifier_types(identifier_t, name, format, private, modifiable, deleted, notifiable, description) values(null, '"+NON_NOTIFIABLE_IDENTIFIER_NAME+"', 'format', false, false, false, false, '"+NON_NOTIFIABLE_IDENTIFIER_NAME+"')");
    }
    
    /**
     * Test determines whether the generation of a new activation key actually constructs a new activation key.
     */
    @Test
    public void testDifferentObjectsOnNewGeneration() {
        final JpaPersonImpl person = new JpaPersonImpl();
        final ActivationKey activationKey = person.getCurrentActivationKey();

        person.generateNewActivationKey(new Date(System.currentTimeMillis() + 10000));

        final ActivationKey activationKeyNew = person.getCurrentActivationKey();

        assertNotSame(activationKey, activationKeyNew);
    }

    /**
     * Tests whether remove activation key actually removes the activation key.
     */
    @Test
    public void testRemoveActivationKey() {
        final JpaPersonImpl person = new JpaPersonImpl();
        assertNotNull(person.getCurrentActivationKey());

        person.removeCurrentActivationKey();
        assertNull(person.getCurrentActivationKey());
    }
    
	
    /**
     * Tests adding one notifiable identifier and one non-notifiable identifier to a person.
     */
    @Test
    public void testAddIdentifiers () {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	person.addIdentifier(referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME), NOTIFIABLE_IDENTIFIER_VALUE);
    	person.addIdentifier(referenceRepository.findIdentifierType(NON_NOTIFIABLE_IDENTIFIER_NAME), NON_NOTIFIABLE_IDENTIFIER_VALUE);
    	assertEquals("The person must have two identifiers", person.getIdentifiers().size(), 2);
    	for (Identifier id : person.getIdentifiers()) {
    		if (id.getType().isNotifiable()) {
    			assertEquals("Identifier value must be as specified", NOTIFIABLE_IDENTIFIER_VALUE, id.getValue());
    		} else {
    			assertEquals("Identifier value must be as specified", NON_NOTIFIABLE_IDENTIFIER_VALUE, id.getValue());
    		}
    	}
    }
    
    @Test
    public void testSetIdentifierNotifiedNonNotifiable() {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	IdentifierType nonNotifiableType = referenceRepository.findIdentifierType(NON_NOTIFIABLE_IDENTIFIER_NAME);
    	IdentifierType notifiableType = referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME);
    	person.addIdentifier(notifiableType, NOTIFIABLE_IDENTIFIER_VALUE);
    	person.addIdentifier(nonNotifiableType, NON_NOTIFIABLE_IDENTIFIER_VALUE);
    	
    	try {
    		person.setIdentifierNotified(nonNotifiableType, new Date());
    		fail("IllegalStateException should be thrown - identifier type incorrect");
    	} catch (IllegalStateException iae) {
    		// expected behavior
    	}
    }
    
    @Test
    public void testSetIdentifierNotifiedNonExistent() {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	IdentifierType nonNotifiableType = referenceRepository.findIdentifierType(NON_NOTIFIABLE_IDENTIFIER_NAME);
    	IdentifierType notifiableType = referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME);
    	person.addIdentifier(nonNotifiableType, NON_NOTIFIABLE_IDENTIFIER_VALUE);
    	
    	try {
    		person.setIdentifierNotified(notifiableType, new Date());
    		fail("IllegalStateException should be thrown - identifier does not exist");
    	} catch (IllegalStateException iae) {
    		// expected behavior
    	}
    }
    
    @Test
    public void testSetIdentifierNotifiedDuplicate() {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	IdentifierType notifiableType = referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME);
    	person.addIdentifier(notifiableType, NOTIFIABLE_IDENTIFIER_VALUE);
    	person.addIdentifier(notifiableType, NOTIFIABLE_IDENTIFIER_VALUE+"1");
    	
    	// this should work - the primary identifier should be updated
    	person.setIdentifierNotified(notifiableType, new Date());
    	try {
    		person.setIdentifierNotified(notifiableType, new Date());
    		fail("IllegalStateException should be thrown - identifier already has a notified date");
    	} catch (IllegalStateException iae) {
    		// expected behavior
    	}
    }
    
    @Test
    public void testSetIdentifierNotified() {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	final Date notificationDate = new Date();
    	
    	IdentifierType nonNotifiableType = referenceRepository.findIdentifierType(NON_NOTIFIABLE_IDENTIFIER_NAME);
    	IdentifierType notifiableType = referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME);
    	person.addIdentifier(notifiableType, NOTIFIABLE_IDENTIFIER_VALUE);
    	person.addIdentifier(nonNotifiableType, NON_NOTIFIABLE_IDENTIFIER_VALUE);
    	
    	assertNull("Notification date must be null before update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate());
    	Identifier returnedId = person.setIdentifierNotified(notifiableType, notificationDate);

    	// verify that the date has been set
    	assertNotNull("Notification date should be set", returnedId.getNotificationDate());
    	assertTrue("This identifier must be primary", returnedId.isPrimary());
    	assertTrue("Notification date should be set to the correct date", returnedId.getNotificationDate().equals(notificationDate));
    	assertNotNull("Notification date must not be null after update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate());
    	assertTrue("Notification date should be set to the correct date after update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate().equals(notificationDate));
    }
    
    @Test
    public void testSetIdentifierNotifiedTwice() {
    	final JpaPersonImpl person = new JpaPersonImpl();
    	final Date notificationDate = new Date();
    	final String secondIdValue = NOTIFIABLE_IDENTIFIER_VALUE+"1";
    	
    	IdentifierType notifiableType = referenceRepository.findIdentifierType(NOTIFIABLE_IDENTIFIER_NAME);
    	Identifier id = person.addIdentifier(notifiableType, NOTIFIABLE_IDENTIFIER_VALUE);
    	
    	// Set the date on first identifier
    	Identifier returnedId = person.setIdentifierNotified(notifiableType, notificationDate);
    	
    	// verify that the date has been set
    	assertNotNull("Notification date should be set", returnedId.getNotificationDate());
    	assertTrue("Notification date should be set to the correct date", returnedId.getNotificationDate().equals(notificationDate));
    	assertTrue("This identifier must be primary", returnedId.isPrimary());
    	assertNotNull("Notification date must not be null after update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate());
    	assertTrue("Notification date should be set to the correct date after update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate().equals(notificationDate));
    	
    	// add a second identifier
    	id.setPrimary(false);
    	Identifier secondId = person.addIdentifier(notifiableType, secondIdValue);    	
    	assertNull("Notification date must be null before update", findIdentifier(person, notifiableType, secondIdValue).getNotificationDate());
    	secondId.setPrimary(true);
    	
    	// Set the date on second identifier
    	Identifier secondReturnedId = person.setIdentifierNotified(notifiableType, notificationDate);
    	assertNotNull("Notification date should be set", secondReturnedId.getNotificationDate());
    	assertTrue("Notification date should be set to the correct date", secondReturnedId.getNotificationDate().equals(notificationDate));
    	assertTrue("This identifier must be primary", secondReturnedId.isPrimary());
    	assertNotNull("Notification date must not be null after update", findIdentifier(person, notifiableType, secondIdValue).getNotificationDate());
    	assertTrue("Notification date should be set to the correct date after update", findIdentifier(person, notifiableType, NOTIFIABLE_IDENTIFIER_VALUE).getNotificationDate().equals(notificationDate));   	
    }
    
    private Identifier findIdentifier(Person person, IdentifierType type, String value) {    	
    	Deque<Identifier> idsForType = person.getIdentifiersByType().get(type.getName());
    	if (idsForType != null) {
    		for (Identifier id: idsForType) {
    			if (id.getValue().equals(value)) {
    				return id;
    			}
    		}
    	}    		
    	return null;
    }
}
