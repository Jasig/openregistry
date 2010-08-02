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

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.Person;
import org.openregistry.core.domain.Role;
import org.openregistry.core.domain.Type;

/**
 * Notifies a Person about the need to activate an account linked to a certain Identifier
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface IdentiferNotificationStrategy {

	/**
	 * A convenience method that notifies the Person using default contact information
	 * @param person
	 * @param idToNotifyAbout
	 * @param activationKeyString
	 * @throws IllegalArgumentException when any of the arguments is null
	 * @throws IllegalStateException if contact information can't be found
	 */
	public void notifyPerson
		(Person person, Identifier idToNotifyAbout, String activationKeyString)
		throws IllegalArgumentException, IllegalStateException;

	/**
	 * Notifies the Person using the contact information of the given type for the specified role
	 * (for example, notify by email, using the OFFICE address on the supplied role)
	 * If role is null, should call the overloaded method
	 * @param person
	 * @param role
	 * @param addressType
	 * @param idToNotifyAbout
	 * @param activationKeyString
	 * @throws IllegalArgumentException when role is supplied but addressType is null
	 * @throws IllegalStateException when the person does not have the specified role, or the role 
	 * has no address of the specified type
	 */
	public void notifyPerson
		(Person person, Role role, Type addressType, Identifier idToNotifyAbout, String activationKeyString)
		throws IllegalArgumentException, IllegalStateException;

}