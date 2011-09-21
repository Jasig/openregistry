/*
 * Created on May 15, 2011
 */
package org.jasig.openregistry.test.domain;

import java.util.Date;

import org.openregistry.core.domain.sor.SorDisclosureSettings;

public class MockSorDisclosureSettings implements SorDisclosureSettings {

 	private String disclosureCode;
 	private boolean withinGracePeriod;
 	private Date lastUpdateDate;

    public MockSorDisclosureSettings (String code, Date lastUpdateDate, boolean withinGracePeriod) {    	
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
