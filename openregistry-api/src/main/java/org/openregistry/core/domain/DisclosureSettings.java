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

package org.openregistry.core.domain;

import java.util.Date;
import java.util.Map;

import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

/**
 * A set of settings that regulates which information about the person can be publicly disclosed
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DisclosureSettings {

	/** The field names for setting properties */
	enum PropertyNames {BUILDING_IND, ADDRESS_LINES_IND, REGION_IND, BUILDING_DATE, ADDRESS_LINES_DATE, REGION_DATE};

	/**
	 * @return the String code identifying this set of disclosure settings
	 */
	public String getDisclosureCode();
	
	/**
	 * @return true if the person is within his or her grace period, when no
	 * information would be shown until he or she sets the disclosure code 
	 */
	public boolean isWithinGracePeriod();
	
	/**
	 * @return the last date when the disclosure settings were updated
	 */
	public Date getLastUpdateDate();
	
	/**
	 * Set the String code identifying this set of disclosure settings
	 * Changing the code may trigger recalculation of field-specific settings
	 * @param code
	 */
	public void setDisclosureCode(String code);
	
	/**
	 * @param within true if the person is within his or her grace period, when no
	 * information would be shown until he or she sets the disclosure code
	 */
	public void setWithinGracePeriod(boolean within);
	
	/**
	 * Set the last date when the disclosure settings were updated
	 * @param date
	 */
	public void setLastUpdateDate(Date date);
	
	/**
	 * Returns address disclosure settings for the specified affiliation and type
	 * @param affiliation
	 * @param addressType
	 * @return
	 */
	public DisclosureSettingsForAddress getAddressDisclosure(String affiliation, Type addressType);

	/**
	 * Returns a map of address disclosure settings keyed by type
	 * @param affiliationType
	 * @return
	 */
	public Map<Type, DisclosureSettingsForAddress> 
		getAddressDisclosureSettingsForAffiliation(String affiliationType);
	
	/**
	 * Returns email disclosure settings for the specified affiliation and type
	 * @param affiliation
	 * @param type
	 * @return
	 */
	public DisclosureSettingsForEmail getEmailDisclosure(String affiliation, Type type);

	/**
	 * Returns a map of email disclosure settings keyed by type
	 * @param affiliationType
	 * @return
	 */
	public Map<Type, DisclosureSettingsForEmail> 
		getEmailDisclosureSettingsForAffiliation(String affiliationType);

	/**
	 * Returns phone number disclosure settings for the specified affiliation,
	 * address type and phone type
	 * @param affiliation
	 * @param addressType
	 * @param phoneType
	 * @return
	 */
	public DisclosureSettingsForPhone getPhoneDisclosure(String affiliation, Type addressType, Type phoneType);

	/**
	 * Returns a map of Phone disclosure settings keyed by address type
	 * and then phone type
	 * @param affiliationType
	 * @return
	 */
	public Map<Type, Map<Type, DisclosureSettingsForPhone>> 
		getPhoneDisclosureSettingsForAffiliation(String affiliationType);

	/**
	 * Returns URL disclosure settings for the specified affiliation and type
	 * @param affiliation
	 * @param type
	 * @return
	 */
	public DisclosureSettingsForUrl getUrlDisclosure(String affiliation, Type type);

	/**
	 * Returns a map of Url disclosure settings keyed by type
	 * @param affiliationType
	 * @return
	 */
	public Map<Type, DisclosureSettingsForUrl> 
		getUrlDisclosureSettingsForAffiliation(String affiliationType);

	/**
	 * Sets disclosure flags for address that apply to the specified affiliation
	 * and specified address type
	 * @param affiliationType
	 * @param addressType
	 * @param properties
	 */
	public void setAddressDisclousure(Type affiliationType, Type addressType, Map<DisclosureSettings.PropertyNames, Object> properties);
	
	/**
	 * Sets the flag that indicates whether email address of specified type for
	 * the specified affiliation should be public
	 * Implementation should set the update date to today's date
	 * @param affiliationType
	 * @param type
	 * @param isPublic
	 */
	public void setEmailDisclosure(Type affiliationType, Type type, boolean isPublic);

	/**
	 * Sets the flag that indicates whether email address of specified type for
	 * the specified affiliation should be public and the last update date
	 * If the date is not specified, implementation should set it to today's date
	 * @param affiliationType
	 * @param type
	 * @param isPublic
	 * @param updateDate
	 */
	public void setEmailDisclosure(Type affiliationType, Type type, boolean isPublic, Date updateDate);

	/**
	 * Sets the flag that indicates whether the phone number of specified type for
	 * the specified affiliation should be public
	 * Implementation should set the update date to today's date
	 * @param affiliationType
	 * @param addressType
	 * @param phoneType
	 * @param isPublic
	 */
	public void setPhoneDisclosure(Type affiliationType, Type addressType, Type phoneType, boolean isPublic);

	/**
	 * Sets the flag that indicates whether the phone number of specified type for
	 * the specified affiliation should be public
	 * If the date is not specified, implementation should set it to today's date
	 * @param affiliationType
	 * @param addressType
	 * @param phoneType
	 * @param isPublic
	 * @param updateDate
	 */
	public void setPhoneDisclosure(Type affiliationType, Type addressType, Type phoneType, boolean isPublic, Date updateDate);

	/**
	 * Sets the flag that indicates whether the URL of specified type for
	 * the specified affiliation should be public
	 * Implementation should set the update date to today's date
	 * @param affiliationType
	 * @param addressType
	 * @param isPublic
	 */
	public void setUrlDisclosure(Type affiliationType, Type type, boolean isPublic);

	/**
	 * Sets the flag that indicates whether the URL of specified type for
	 * the specified affiliation should be public
	 * If the date is not specified, implementation should set it to today's date
	 * @param affiliationType
	 * @param type
	 * @param isPublic
	 * @param updateDate
	 */
	public void setUrlDisclosure(Type affiliationType, Type type, boolean isPublic, Date updateDate);

	/**
	 * Recalculate all field-level flags based on the supplied strategy
	 * @param disclosureRecalculationStrategy
	 */
	public void recalculate (DisclosureRecalculationStrategy disclosureRecalculationStrategy);
	
	/**
	 * Recalculate all field-level flags based on the supplied strategy and affiliationType
	 * @param disclosureRecalculationStrategy
	 */
	public void recalculate (DisclosureRecalculationStrategy disclosureRecalculationStrategy,String affiliation,ReferenceRepository referenceRepository);
}
