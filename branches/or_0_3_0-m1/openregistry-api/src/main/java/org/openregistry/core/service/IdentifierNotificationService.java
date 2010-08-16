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

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Type;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The service that notifies a Person when actions are required in relation 
 * to one of his/her Identifiers, for example, to activate an account or 
 * change a password
 * The contents of the message and the method of sending it is up to the 
 * implementation. The name, contact information and activation key should be
 * part of the person object
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentifierNotificationService {
	
	/**
	 * An overloaded version of sendAccountActivationNotification() that does not
	 * take the role into account. 
	 * This is necessitated by the fact that some implementations may not require  
	 * a role for account to be activated
	 * @param person
	 * @param identifierType
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void sendAccountActivationNotification
		(Person person, IdentifierType identifierType) 
		throws IllegalArgumentException, IllegalStateException;
	
	/**
	 * Notify the person that his or her account needs to be activated.
	 * If successful, update the notification date on the corresponding identifier
	 * @param person the person to be notified
	 * @param identifierType the type of identifier to be used for activation
	 * @param role the role that has been added, prompting the activation
	 * @param addressType the type of email address to send notification to
	 * @throws IllegalArgumentException when any of the arguments is null 
	 * @throws IllegalStateException when identifier of supplied type already has 
	 * notification date set, or Person has no identifiers of the supplied type, 
	 * or is missing the supplied Role, or if email address can't be found on the role
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void sendAccountActivationNotification
		(Person person, IdentifierType identifierType, Role role, Type addressType) 
		throws IllegalArgumentException, IllegalStateException;
}
