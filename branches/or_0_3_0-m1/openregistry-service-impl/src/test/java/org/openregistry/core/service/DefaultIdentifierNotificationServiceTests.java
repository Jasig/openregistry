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

import org.jasig.openregistry.test.domain.MockPerson;
import org.jasig.openregistry.test.domain.MockSorName;
import org.jasig.openregistry.test.domain.MockIdentifierType;
import org.jasig.openregistry.test.domain.MockSorRole;
import org.jasig.openregistry.test.domain.MockType;
import org.jasig.openregistry.test.repository.MockPersonRepository;
import org.jasig.openregistry.test.service.MockMailSender;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorRole;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Test cases for the {@link org.openregistry.core.service.DefaultIdentifierNotificationService}.  
 * Note this does not actually send any emails.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class DefaultIdentifierNotificationServiceTests {

    private DefaultIdentifierNotificationService notificationService;
    private MockPersonRepository mockPersonRepository = new MockPersonRepository();

    @Before
    public void setUp() throws Exception {
    	ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    	ms.setBasename("identifier-email-messages");
        this.notificationService = 
        	new DefaultIdentifierNotificationService
        		(mockPersonRepository,
        		new DefaultEmailIdentifierNotificationStrategy(new MockMailSender(), ms),
        		30);
    }

    @Test
    public void sendAccountActivationNotificationWithoutRole() {
    	// Create a valid person with a name, a notifiable identifier and an activation key
    	Person person = new MockPerson("testid", false, false);
    	person.addName(createMockSorName("Firstname", "Lastname"));
    	person.getPreferredContactEmailAddress().setAddress("test-preferred@test.test");
    	IdentifierType identifierType = new MockIdentifierType("testNotifiableId", true);
    	Identifier id = person.addIdentifier(identifierType, "notifiableId");
    	id.setPrimary(true);
    	
    	mockPersonRepository.getPersons().clear();
    	mockPersonRepository.getPersons().add(person);
    	
		notificationService.sendAccountActivationNotification(person, identifierType);
    }
    
    @Test
    public void sendAccountActivationWithExpiredActivationKey() {
    	// Create a valid person with a name, a notifiable identifier and an expired activation key
    	Person person = new MockPerson("testid", false, true);
    	person.addName(createMockSorName("Firstname", "Lastname"));
    	person.getPreferredContactEmailAddress().setAddress("test-preferred@test.test");
    	IdentifierType identifierType = new MockIdentifierType("testNotifiableId", true);
    	Identifier id = person.addIdentifier(identifierType, "notifiableId");
    	id.setPrimary(true);
    	
    	mockPersonRepository.getPersons().clear();
    	mockPersonRepository.getPersons().add(person);
    	
    	notificationService.sendAccountActivationNotification(person, identifierType);
    }
    
    @Test
    public void sendAccountActivationWithInvalidActivationKey() {
    	// Create a valid person with a name, a notifiable identifier and no activation key
    	Person person = new MockPerson("testid", true, false);
    	person.addName(createMockSorName("Firstname", "Lastname"));
    	person.getPreferredContactEmailAddress().setAddress("test-preferred@test.test");
    	IdentifierType identifierType = new MockIdentifierType("testNotifiableId", true);
    	Identifier id = person.addIdentifier(identifierType, "notifiableId");
    	id.setPrimary(true);
    	
    	mockPersonRepository.getPersons().clear();
    	mockPersonRepository.getPersons().add(person);
    	
    	try {
    		notificationService.sendAccountActivationNotification(person, identifierType);
    		fail ("IllegalStateException must be thrown");
    	} catch (IllegalStateException ise) {
    		assertTrue("The IllegalStateException must contain a message about activation key", ise.getMessage().indexOf("activation key") >=0);
    	}
    }
    
    @Test
    public void sendAccountActivationNotificationWithRole() {
    	// Create a valid person with a name, a notifiable identifier and an activation key
    	Person person = new MockPerson("testid", false, false);
    	person.addName(createMockSorName("Firstname", "Lastname"));
    	
    	// add identifier
    	IdentifierType identifierType = new MockIdentifierType("testNotifiableId", true);
    	Identifier id = person.addIdentifier(identifierType, "notifiableId");
    	id.setPrimary(true);
    	
    	// add role with a home email address
    	SorRole sorRole = new MockSorRole();
    	Type type = new MockType("emailType", "HOME");
    	sorRole.addOrUpdateEmail("test@test.test", type);
    	Role role = person.addRole(sorRole);
    	
    	mockPersonRepository.getPersons().clear();
    	mockPersonRepository.getPersons().add(person);
    	
    	notificationService.sendAccountActivationNotification(person, identifierType, role, type);
    }
    
    private SorName createMockSorName(String fn, String ln) {
    	SorName name = new MockSorName();
    	name.setGiven(fn);
    	name.setFamily(ln);
    	return name;
    }
}
