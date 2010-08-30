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

package org.jasig.openregistry.test.domain;

import java.util.Date;

import org.openregistry.core.domain.DisclosureSettings;

/**
 * Disclosure settings entity mapped to a persistence store with JPA annotations.
 *
 * Unique constraints assume that each person can have only one record of disclosure settings
 *
 * @author lidial
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

public class MockDisclosureSettings implements DisclosureSettings {

 	private String disclosureCode;
 	private boolean withinGracePeriod;
 	private Date lastUpdateDate;

    public MockDisclosureSettings (String code, Date lastUpdateDate, boolean withinGracePeriod) {    	
		this.disclosureCode = code;
		this.withinGracePeriod = withinGracePeriod;
		this.lastUpdateDate = new Date (lastUpdateDate.getTime());
	}
    
    /**
	 * @see org.openregistry.core.domain.DisclosureSettings#getDisclosureCode()
	 */
	public String getDisclosureCode() {
		return disclosureCode;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getLastUpdateDate()
	 */
	public Date getLastUpdateDate() {
		return new Date (lastUpdateDate.getTime());
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#isWithinGracePeriod()
	 */
	public boolean isWithinGracePeriod() {
		return withinGracePeriod;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setDisclosureCode(java.lang.String)
	 */
	public void setDisclosureCode(String code) {
		this.disclosureCode = code;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setLastUpdateDate(java.util.Date)
	 */
	public void setLastUpdateDate(Date date) {
		this.lastUpdateDate = (date !=null) ? new Date(date.getTime()) : null;
		
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setWithinGracePeriod(boolean)
	 */
	public void setWithinGracePeriod(boolean within) {
		this.withinGracePeriod = within;
	}	
}