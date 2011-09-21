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

import java.util.Map;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.PersonNotFoundException;
import org.openregistry.core.domain.Type;

/**
 * A service for looking up and updating disclosure settings
 * @author llevkovi 
 * @version $Id$
 */
public interface DisclosureService {
	
	/**
	 * Returns the Map of all address-related disclosure settings for given role (keyed by address type)
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @return
	 */
	public Map<Type, DisclosureSettingsForAddress> getAddressDisclosureSettingsForAffiliation
		(String identifierType, String identifierValue, String affiliationType) throws PersonNotFoundException ; 

	/**
	 * Returns address-related disclosure settings for given role and address type
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @param addressType
	 * @return
	 */
	public DisclosureSettingsForAddress getAddressDisclosureSettingsForRoleAndType
		(String identifierType, String identifierValue, String affiliationType, Type addressType)
	 throws PersonNotFoundException; 
	
	/**
	 * Returns the Map of all email-related disclosure settings for given role (keyed by type)
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public Map<Type, DisclosureSettingsForEmail> getEmailDisclosureSettingsForAffiliation
		(String identifierType, String identifierValue, String affiliationType) throws PersonNotFoundException ; 

	/**
	 * Returns email-related disclosure settings for given role and type
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @param emailType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public DisclosureSettingsForEmail getEmailDisclosureSettingsForRoleAndType
		(String identifierType, String identifierValue, String affiliationType, Type emailType)
	 	throws PersonNotFoundException; 

	/**
	 * Returns the Map of Maps all phone-related disclosure settings for given role 
	 * (keyed by address type then phone type)
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public Map<Type, Map<Type, DisclosureSettingsForPhone>> getPhoneDisclosureSettingsForAffiliation
		(String identifierType, String identifierValue, String affiliationType) throws PersonNotFoundException ; 

	/**
	 * Returns phone-related disclosure settings for given role, address type and phone type
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @param addressType
	 * @param phoneType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public DisclosureSettingsForPhone getPhoneDisclosureSettingsForRoleAndType
		(String identifierType, String identifierValue, String affiliationType, Type addressType, Type phoneType)
 		throws PersonNotFoundException; 

	/**
	 * Returns the Map of all URL-related disclosure settings for given role (keyed by type)
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public Map<Type, DisclosureSettingsForUrl> getUrlDisclosureSettingsForAffiliation
	(String identifierType, String identifierValue, String affiliationType) throws PersonNotFoundException ; 

	/**
	 * Returns URL-related disclosure settings for given role and type
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliationType
	 * @param urlType
	 * @return
	 * @throws PersonNotFoundException
	 */
	public DisclosureSettingsForUrl getUrlDisclosureSettingsForRoleAndType
		(String identifierType, String identifierValue, String affiliationType, Type urlType)
	 	throws PersonNotFoundException; 

	/**
	 * Sets address disclosure settings for a specific type and role, finding the person by identifier
	 * Validation should make sure that the flags are not in conflict with the overall disclosure setting
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliation
	 * @param addressType
	 * @param addressDisclosureProps
	 * @return
	 */
	public ServiceExecutionResult<DisclosureSettingsForAddress> setAddressDisclosureSettings
		(String identifierType, String identifierValue, String affiliation,
			Type addressType, Map<DisclosureSettings.PropertyNames, Object> addressDisclosureProps)
		throws PersonNotFoundException;

	/**
	 * Sets email public/private flag for a specific type and role, finding the person by identifier
	 * Validation should make sure that the flag is not in conflict with the overall disclosure setting
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliation
	 * @param emailType
	 * @param isPublic
	 * @return
	 * @throws PersonNotFoundException
	 */
	public ServiceExecutionResult<DisclosureSettingsForEmail> setEmailDisclosureSettings
	(String identifierType, String identifierValue, String affiliation,
			Type emailType, boolean isPublic)
		throws PersonNotFoundException;

	/**
	 * Sets phone public/private flag for a specific type and role, finding the person by identifier
	 * Validation should make sure that the flag is not in conflict with the overall disclosure setting
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliation
	 * @param addressType
	 * @param phoneType
	 * @param isPublic
	 * @return
	 * @throws PersonNotFoundException
	 */
	public ServiceExecutionResult<DisclosureSettingsForPhone> setPhoneDisclosureSettings
	(String identifierType, String identifierValue, String affiliation,
			Type addressType, Type phoneType, boolean isPublic)
		throws PersonNotFoundException;

	/**
	 * Sets URL public/private flag for a specific type and role, finding the person by identifier
	 * Validation should make sure that the flag is not in conflict with the overall disclosure setting
	 * @param identifierType
	 * @param identifierValue
	 * @param affiliation
	 * @param urlType
	 * @param isPublic
	 * @return
	 * @throws PersonNotFoundException
	 */
	public ServiceExecutionResult<DisclosureSettingsForUrl> setUrlDisclosureSettings
	(String identifierType, String identifierValue, String affiliation,
			Type urlType, boolean isPublic)
		throws PersonNotFoundException;
}
