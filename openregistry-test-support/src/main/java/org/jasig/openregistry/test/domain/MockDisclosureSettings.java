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
import java.util.Map;

import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

public class MockDisclosureSettings implements DisclosureSettings {

 	private String disclosureCode;
 	private boolean withinGracePeriod;
 	private Date lastUpdateDate;

    public MockDisclosureSettings 
    	(String code, Date lastUpdateDate, boolean withinGracePeriod) {    	
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

	@Override
	public DisclosureSettingsForAddress getAddressDisclosure(
			String affiliation, Type addressType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Type, DisclosureSettingsForAddress> getAddressDisclosureSettingsForAffiliation(
			String affiliationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisclosureSettingsForEmail getEmailDisclosure(String affiliation,
			Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisclosureSettingsForPhone getPhoneDisclosure(String affiliation,
			Type addressType, Type phoneType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisclosureSettingsForUrl getUrlDisclosure(String affiliation,
			Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddressDisclousure(Type affiliationType, Type addressType,
			Map<PropertyNames, Object> properties) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setEmailDisclosure(Type affiliationType, Type type,
			boolean isPublic) {
		setEmailDisclosure(affiliationType, type, isPublic, null);
	}

	@Override
	public void setPhoneDisclosure(Type affiliationType, Type addressType,
			Type phoneType, boolean isPublic) {
		setPhoneDisclosure(affiliationType, addressType, phoneType, isPublic, null);
	}

	@Override
	public void setUrlDisclosure(Type affiliationType, Type type,
			boolean isPublic) {
		setUrlDisclosure(affiliationType, type, isPublic, null);
	}

	@Override
	public void recalculate(DisclosureRecalculationStrategy disclosureRecalculationStrategy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Type, DisclosureSettingsForEmail> getEmailDisclosureSettingsForAffiliation(
			String affiliationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Type, Map<Type, DisclosureSettingsForPhone>> getPhoneDisclosureSettingsForAffiliation(
			String affiliationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Type, DisclosureSettingsForUrl> getUrlDisclosureSettingsForAffiliation(
			String affiliationType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmailDisclosure(Type affiliationType, Type type,
			boolean isPublic, Date updateDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPhoneDisclosure(Type affiliationType, Type addressType,
			Type phoneType, boolean isPublic, Date updateDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUrlDisclosure(Type affiliationType, Type type,
			boolean isPublic, Date updateDate) {
		// TODO Auto-generated method stub
		
	}	
}