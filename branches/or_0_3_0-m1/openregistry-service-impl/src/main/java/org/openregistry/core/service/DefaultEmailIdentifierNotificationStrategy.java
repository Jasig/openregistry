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

import java.util.ArrayList;

import javax.inject.Inject;

import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Type;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Uses email to notify a Person about the need to activate an account linked to a certain Identifier
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class DefaultEmailIdentifierNotificationStrategy implements
		IdentiferNotificationStrategy {
    
	private final MailSender mailSender;
	private final MessageSource messageSource;
	
    @Inject
    /**
     * @param mailSender - the mail sender
     * @param messageSource - the source of text needed for email generation
     */
    public DefaultEmailIdentifierNotificationStrategy(MailSender mailSender,
    		MessageSource messageSource) {
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }
    
	public void notifyPerson(Person person, Identifier idToNotifyAbout,
			String activationKeyString) throws IllegalArgumentException, IllegalStateException {
		notifyPerson(person, null, null, idToNotifyAbout, activationKeyString);
	}

	public void notifyPerson(Person person, Role role, Type addressType,
			Identifier idToNotifyAbout, String activationKeyString)
			throws IllegalArgumentException, IllegalStateException {

		if (person == null) {
			throw new IllegalArgumentException("Person must be specified");
		}
		if (idToNotifyAbout == null) {
			throw new IllegalArgumentException("Identifier must be specified");
		}
		
		Name officialName = person.getOfficialName();
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo(findEmailAddressesForNotification(person, role, addressType));
		msg.setFrom(messageSource.getMessage("activation.notify.from", null, null));
		msg.setSubject(messageSource.getMessage("activation.notify.subject", null, null));
		String greeting = (officialName == null) ? "" : officialName.getFormattedName();
		msg.setText(messageSource.getMessage
				("activation.notify.body", 
				new String[] {greeting,idToNotifyAbout.getValue(),activationKeyString},
				null));
		mailSender.send(msg);
	}
	
	/**
	 * Finds email addresses to which notifications will be sent and returns them as an array
	 * @param person
	 * @param role
	 * @param addressType
	 * @return
	 */
	private String[] findEmailAddressesForNotification(Person person, Role role, Type addressType) {
		
		ArrayList<String> addresses = new ArrayList<String>();
		
		if (role != null) {
			
			if (addressType == null) {
				throw new IllegalArgumentException("Address type must be specified");
			} else {		
				// Find the email address of the correct type for the specified Role
				if (role.getEmailAddresses() != null) {
					for (EmailAddress ea : role.getEmailAddresses()) {
						if (ea.getAddressType().isSameAs(addressType)) {
							addresses.add(ea.getAddress());
						}
					}
				} 
			}
		} else {
			// role was not supplied, use the default address
			EmailAddress preferredEmailAddress = person.getPreferredContactEmailAddress();
			if (preferredEmailAddress == null || preferredEmailAddress.getAddress() == null) {
				throw new IllegalStateException("A preferred email address is required to send notification"); 
			} else {
				addresses.add(preferredEmailAddress.getAddress());
			}
		}
		
		if (addresses.isEmpty()) {
			throw new IllegalStateException("The role has no email address of type "+addressType.getDescription()+" associated with it");		
		} else {
			return addresses.toArray(new String[addresses.size()]);
		}
	}
}
