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

package org.openregistry.core.domain.jpa.sor;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
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

@Entity(name = "sorDisclosure")
@Table(name = "prs_disclosure", uniqueConstraints = @UniqueConstraint(columnNames = {"sor_person_id","disclosure_code"}))
@Audited
public class JpaSorDisclosureSettingsImpl implements DisclosureSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_disclosure_records_seq")
    @SequenceGenerator(name = "prs_disclosure_records_seq", sequenceName = "prs_disclosure_records_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @OneToOne(optional=false)
    @JoinColumn(name="sor_person_id")
    private JpaSorPersonImpl person;

    @Column(name="disclosure_code", nullable=false, length=10)
	private String disclosureCode;

    @Column(name="updated_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

    @Column(name="within_grace_period", nullable=false)
    private boolean withinGracePeriod = false;

    /**
     * Default constructor
     */
    public JpaSorDisclosureSettingsImpl () {}
    
    /**
     * Initializing constructor
     * @param person
     * @param code
     * @param lastUpdateDate
     * @param withinGracePeriod
     */
    public JpaSorDisclosureSettingsImpl 
    	(JpaSorPersonImpl person, String code, Date lastUpdateDate, boolean withinGracePeriod) {    	
    	this.person = person;
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
		return new Date(this.lastUpdateDate.getTime());
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#isWithinGracePeriod()
	 */
	public boolean isWithinGracePeriod() {
		return this.withinGracePeriod;
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
		this.lastUpdateDate = (date != null) ? new Date(date.getTime()): null;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setWithinGracePeriod(boolean)
	 */
	public void setWithinGracePeriod(boolean within) {
		this.withinGracePeriod = within;
	}
}
