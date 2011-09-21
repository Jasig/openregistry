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

import org.openregistry.core.service.DisclosureRecalculationStrategy;

/**
 * A set of settings that regulates which information about a URL
 * can be publicly disclosed
 * @author lidial 
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface DisclosureSettingsForUrl {
    
	/** Unique identifier */
	public Long getId();
	
	/** The type of URL this setting should apply to */
	public Type getType();

	/** Returns affiliation as String */
	public String getAffiliation();

	/** Returns true if this URL should be public */
	boolean getPublicInd();
	
	/** Returns the date when the disclosure setting was last updated */
	Date getPublicIndDate();

	/** Controls whether the URL should be public; sets the last update date to the current date */
	void setPublicInd(boolean isPublic);
	
	/** Controls whether the URL should be public and sets the last update date*/
	void setPublicInd(boolean isPublic, Date updateDate);
	
	/**
	 * Returns true if the object's type and affiliation type match
	 * the supplied values
	 * @param type
	 * @param affiliation
	 * @return
	 */
	public boolean matchesTypeAndAffiliation(Type type, String affiliation);

	/**
	 * Checks whether the supplied disclosure strategy overrides any of the values
	 * and updates them accordingly
	 * @param disclosureCode
	 * @param disclosureSettingRecalculationStrategy
	 */
	public void recalculate(String disclosureCode, DisclosureRecalculationStrategy disclosureSettingRecalculationStrategy);
}
