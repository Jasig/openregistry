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

import org.openregistry.core.service.DisclosureRecalculationStrategy;

/**
 * A set of settings that regulates which information about the address
 * can be publicly disclosed
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DisclosureSettingsForAddress {
    
	/** Returns true if building and room information should be public */
	boolean getAddressBuildingPublicInd();
	
	/** Returns the date building and room information disclosure setting was last updated */
	Date getAddressBuildingPublicIndDate();
	
	/** Returns true if lines of address 1 through 3 should be public */
	boolean getAddressLinesPublicInd();
	
	/** Returns the date street address information disclosure setting was last updated */
	Date getAddressLinesPublicIndDate();
	
	/** Returns true if city, region and postal code should be public */
	boolean getAddressRegionPublicInd();
	
	/** Returns the date city, region and postal code disclosure setting was last updated */
	Date getAddressRegionPublicIndDate();
	
	/** Sets supplied property values */
	public void initFromProperties(Map<DisclosureSettings.PropertyNames, Object> properties);
	
	/** Returns the address type */
	public Type getAddressType();

	/** Returns affiliation as String */
	public String getAffiliation();
	
	/**
	 * Returns true if the object's address type and affiliation type match
	 * the supplied values
	 * @param addressType
	 * @param affiliation
	 * @return
	 */
	public boolean matchesTypeAndAffiliation(Type addressType, String affiliation);
	
	/**
	 * Checks whether the supplied disclosure strategy overrides any of the values
	 * and updates them accordingly
	 * @param disclosureCode
	 * @param disclosureSettingRecalculationStrategy
	 */
	public void recalculate(String disclosureCode, DisclosureRecalculationStrategy disclosureSettingRecalculationStrategy);
}
