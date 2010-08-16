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

import java.util.Calendar;

import javax.inject.Inject;

import org.openregistry.core.domain.ActivationKey;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Type;
import org.openregistry.core.repository.PersonRepository;

/**
 * Default implementation of <code>IdentifierNotificationService</code>
 * Sends email notifications 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class DefaultIdentifierNotificationService implements
		IdentifierNotificationService {

    private final PersonRepository personRepository;
    private final DateGenerator dateGenerator = new CurrentDateTimeDateGenerator();
    private final DateGenerator endDateGenerator;
    private final IdentiferNotificationStrategy notificationStrategy;
    
    @Inject
    public DefaultIdentifierNotificationService(
    		final PersonRepository personRepository,
    		final IdentiferNotificationStrategy notificationStrategy,
    		final int daysForActivationKey) {
        this.personRepository = personRepository;
        this.notificationStrategy = notificationStrategy;
        this.endDateGenerator = new AdditiveDateTimeDateGenerator(Calendar.DAY_OF_MONTH, daysForActivationKey);
    }

	/**
	 * Sends an email notification to the Person's preferred email address
	 */
    public void sendAccountActivationNotification(Person person,
			IdentifierType identifierType) throws IllegalArgumentException,
			IllegalStateException {

    	sendAccountActivationNotification(person, identifierType, null, null);
	}

	/**
	 * Sends an email notification to the email address for the specified role
	 */
	public void sendAccountActivationNotification(Person person,
			IdentifierType identifierType, Role role, Type addressType)
			throws IllegalArgumentException, IllegalStateException {
		
		ActivationKey activationKey = person.getCurrentActivationKey();
		String activationKeyString = null;

		if (activationKey != null) {
			// if activation key has expired, request a new one
			if (activationKey.isExpired()) {
				activationKey = person.generateNewActivationKey(dateGenerator.getNewDate(), endDateGenerator.getNewDate());
			}
			if (activationKey.isValid()) {
				activationKeyString = activationKey.asString();
			}
		}
		
		if (activationKeyString == null) {
				// If we did not have a valid activation key, and failed to obtain one throw exception
				throw new IllegalStateException("A valid activation key is required to send notification"); 
		} else {
			// Locate the identifier of supplied type that requires notification		
			// and update the identifier
			Identifier idToNotifyAbout = person.getPrimaryIdentifiersByType().get(identifierType.getName());
			
			if (idToNotifyAbout == null) {
				throw new IllegalStateException("An identifier to send notification about was not found"); 
			} else {
				// Otherwise, if the notification date has already been set, do nothing
				if (idToNotifyAbout.getNotificationDate() == null) {
					
					// Retrieve the latest person data
					Person personToUpdate = this.personRepository.findByIdentifier(identifierType.getName(), idToNotifyAbout.getValue());
				
					if (personToUpdate == null) {
						throw new IllegalStateException("Unable to retrieve person data from the database");
					} else {
						person.setIdentifierNotified(identifierType, this.dateGenerator.getNewDate());
						this.notificationStrategy.notifyPerson(person, role, addressType, idToNotifyAbout, activationKeyString);
						this.personRepository.savePerson(personToUpdate);		
					}
				}
			}
		}
	}
}
