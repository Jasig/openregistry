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

package org.openregistry.core.domain.jpa;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.DisclosureSettingsForEmail;
import org.openregistry.core.domain.DisclosureSettingsForPhone;
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

import java.io.Serializable;

/**
 * Disclosure settings entity mapped to a persistence store with JPA annotations.
 *
 * Unique constraints assume that each person can have only one record of disclosure settings
 *
 * @author lidial
 * @version $Revision$ $Date$
 * @since 1.0.0
 */

@Entity(name = "disclosure")
@Table(name = "prc_disclosure", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id"}))
@Audited
public class JpaDisclosureSettingsImpl implements DisclosureSettings, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_disclosure_records_seq")
    @SequenceGenerator(name = "prc_disclosure_records_seq", sequenceName = "prc_disclosure_records_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @OneToOne(optional=false)
    @JoinColumn(name="person_id")
    private JpaPersonImpl person;

    @Column(name="disclosure_code", nullable=true, length=10)
	private String disclosureCode;
    
    @Column(name="updated_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

    @Column(name="within_grace_period", nullable=false)
    private boolean withinGracePeriod = false;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disclosureRecord", targetEntity = JpaDisclosureSettingsForAddressImpl.class)
    private Set<DisclosureSettingsForAddress> addressDsiclosureSettings = new HashSet<DisclosureSettingsForAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disclosureRecord", targetEntity = JpaDisclosureSettingsForEmailImpl.class)
    private Set<DisclosureSettingsForEmail> emailDsiclosureSettings = new HashSet<DisclosureSettingsForEmail>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disclosureRecord", targetEntity = JpaDisclosureSettingsForPhoneImpl.class)
    private Set<DisclosureSettingsForPhone> phoneDsiclosureSettings = new HashSet<DisclosureSettingsForPhone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disclosureRecord", targetEntity = JpaDisclosureSettingsForUrlImpl.class)
    private Set<DisclosureSettingsForUrl> urlDsiclosureSettings = new HashSet<DisclosureSettingsForUrl>();

    @Transient
	private boolean isDirty = false;
    
    /**
     * Default constructor
     */
    public JpaDisclosureSettingsImpl () {}
    
    /**
     * Initializing constructor
     * @param person
     * @param code
     */
    public JpaDisclosureSettingsImpl (JpaPersonImpl person) {    	
    	this.person = person;
    }
   
    /** 
     * A listener that makes sure that the overall flag is in sync
     * with the individual flags before saving
     * Throws IllegalStateException if this object has unreconciled changes
     */
    @PrePersist
    @PreUpdate
    void checkState() {
		if (this.isDirty) {
			throw new IllegalStateException("The overall disclosure setting may be inconsistent with field-level flags. Please call recalculate()");
		}
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
		return (this.lastUpdateDate != null ) ?
			new Date(this.lastUpdateDate.getTime()) : null;
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
		this.isDirty = true;
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

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getAddressDisclosure(java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForAddress getAddressDisclosure(String affiliation, Type addressType) {
		return findAddressDisclosure(addressType, affiliation);
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getAddressDisclosureSettingsForAffiliation(java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForAddress> getAddressDisclosureSettingsForAffiliation(String affiliationType) {
		Map <Type, DisclosureSettingsForAddress> addressDisclosureMap = new HashMap<Type, DisclosureSettingsForAddress>();
		for (DisclosureSettingsForAddress ad : this.addressDsiclosureSettings) {
			if (ad.getAffiliation().equals(affiliationType)) {
				addressDisclosureMap.put(ad.getAddressType(), ad);
			}
		}
		return addressDisclosureMap;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getEmailDisclosure(java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForEmail getEmailDisclosure(String affiliation, Type type) {
		return findEmailDisclosure(type, affiliation);
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getEmailDisclosureSettingsForAffiliation(java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForEmail> getEmailDisclosureSettingsForAffiliation(
			String affiliationType) {
		Map <Type, DisclosureSettingsForEmail> emailDisclosureMap = new HashMap<Type, DisclosureSettingsForEmail>();
		for (DisclosureSettingsForEmail ed : this.emailDsiclosureSettings) {
			if (ed.getAffiliation().equals(affiliationType)) {
				emailDisclosureMap.put(ed.getType(), ed);
			}
		}
		return emailDisclosureMap;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getPhoneDisclosure(java.lang.String, org.openregistry.core.domain.Type, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForPhone getPhoneDisclosure(String affiliation, Type addressType, Type phoneType) {
		return findPhoneDisclosure(addressType, phoneType, affiliation);
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getPhoneDisclosureSettingsForAffiliation(java.lang.String)
	 */
	public Map<Type, Map<Type, DisclosureSettingsForPhone>> getPhoneDisclosureSettingsForAffiliation(
			String affiliationType) {
		Map<Type, Map<Type, DisclosureSettingsForPhone>> phoneDisclosureMap = new HashMap<Type, Map<Type, DisclosureSettingsForPhone>>();
		for (DisclosureSettingsForPhone pd : this.phoneDsiclosureSettings) {
			if (pd.getAffiliation().equals(affiliationType)) {
				Map <Type, DisclosureSettingsForPhone> phoneDisclosureMapForAddressType =
					phoneDisclosureMap.remove(pd.getAddressType());
				if (phoneDisclosureMapForAddressType == null) {
					phoneDisclosureMapForAddressType = new HashMap<Type, DisclosureSettingsForPhone>();
				}
				phoneDisclosureMapForAddressType.put(pd.getPhoneType(), pd);
				phoneDisclosureMap.put(pd.getAddressType(), phoneDisclosureMapForAddressType);
			}
		}
		return phoneDisclosureMap;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getUrlDisclosure(java.lang.String, org.openregistry.core.domain.Type)
	 */
	public DisclosureSettingsForUrl getUrlDisclosure(String affiliation, Type type) {
		return findUrlDisclosure(type, affiliation);
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#getUrlDisclosureSettingsForAffiliation(java.lang.String)
	 */
	public Map<Type, DisclosureSettingsForUrl> getUrlDisclosureSettingsForAffiliation(String affiliationType) {
		Map <Type, DisclosureSettingsForUrl> urlDisclosureMap = new HashMap<Type, DisclosureSettingsForUrl>();
		for (DisclosureSettingsForUrl ud : this.urlDsiclosureSettings) {
			if (ud.getAffiliation().equals(affiliationType)) {
				urlDisclosureMap.put(ud.getType(), ud);
			}
		}
		return urlDisclosureMap;
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setAddressDisclousure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, java.util.Map)
	 */
	public void setAddressDisclousure(Type affiliationType, Type addressType, Map<PropertyNames, Object> properties) {
		DisclosureSettingsForAddress ad = this.findAddressDisclosure(addressType, affiliationType.getDescription());
		if (ad == null) {
			ad = new JpaDisclosureSettingsForAddressImpl(this, addressType, affiliationType);
			ad.initFromProperties(properties);
			this.addressDsiclosureSettings.add(ad);
		} else {
			ad.initFromProperties(properties);
		}
		this.isDirty = true;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setEmailDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean)
	 */
	public void setEmailDisclosure(Type affiliationType, Type type, boolean isPublic) {
		setEmailDisclosure(affiliationType, type, isPublic, null);
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setEmailDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean, java.util.Date)
	 */
	public void setEmailDisclosure(Type affiliationType, Type type, boolean isPublic, Date updateDate) {
		if (updateDate == null) {
			updateDate = new Date();
		}
		DisclosureSettingsForEmail ed = this.findEmailDisclosure(type, affiliationType.getDescription());
		if (ed == null) {
			ed = new JpaDisclosureSettingsForEmailImpl(this, type, affiliationType);
			ed.setPublicInd(isPublic, updateDate);
			this.emailDsiclosureSettings.add(ed);
		} else {
			ed.setPublicInd(isPublic, updateDate);
		}
		this.isDirty = true;
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setPhoneDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean)
	 */
	public void setPhoneDisclosure(Type affiliationType, Type addressType, Type phoneType, boolean isPublic) {
		setPhoneDisclosure(affiliationType, addressType, phoneType, isPublic,null);
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setPhoneDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean, java.util.Date)
	 */
	public void setPhoneDisclosure(Type affiliationType, Type addressType, Type phoneType, boolean isPublic, Date updateDate) {
		if (updateDate == null) {
			updateDate = new Date();
		}
		DisclosureSettingsForPhone pd = this.findPhoneDisclosure(addressType, phoneType, affiliationType.getDescription());
		if (pd == null) {
			pd = new JpaDisclosureSettingsForPhoneImpl(this, addressType, phoneType, affiliationType);
			pd.setPublicInd(isPublic, updateDate);
			this.phoneDsiclosureSettings.add(pd);
		} else {
			pd.setPublicInd(isPublic, updateDate);
		}
		this.isDirty = true;		
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setUrlDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean)
	 */
	public void setUrlDisclosure(Type affiliationType, Type type, boolean isPublic) {
		setUrlDisclosure(affiliationType, type, isPublic, null);
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#setUrlDisclosure(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type, boolean, java.util.Date)
	 */
	public void setUrlDisclosure(Type affiliationType, Type type, boolean isPublic, Date updateDate) {
		if (updateDate == null) {
			updateDate = new Date();
		}
		DisclosureSettingsForUrl ud = this.findUrlDisclosure(type, affiliationType.getDescription());
		if (ud == null) {
			ud = new JpaDisclosureSettingsForUrlImpl(this, type, affiliationType);
			ud.setPublicInd(isPublic, updateDate);
			this.urlDsiclosureSettings.add(ud);
		} else {
			ud.setPublicInd(isPublic, updateDate);
		}
		this.isDirty = true;
	}	
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettings#recalculate(org.openregistry.core.service.DisclosureRecalculationStrategy)
	 */
	public void recalculate (DisclosureRecalculationStrategy disclosureRecalculationStrategy) {
		for (DisclosureSettingsForAddress ad: addressDsiclosureSettings) {
			ad.recalculate(this.disclosureCode, disclosureRecalculationStrategy);
		}		
		for (DisclosureSettingsForEmail ed: emailDsiclosureSettings) {
			ed.recalculate(this.disclosureCode, disclosureRecalculationStrategy);
		}		
		for (DisclosureSettingsForPhone pd: phoneDsiclosureSettings) {
			pd.recalculate(this.disclosureCode, disclosureRecalculationStrategy);
		}		
		for (DisclosureSettingsForUrl ud: urlDsiclosureSettings) {
			ud.recalculate(this.disclosureCode, disclosureRecalculationStrategy);
		}	
		this.isDirty = false;
	}
	
	/**
	 * Expose the dirty state to JPA implementations of dependent objects
	 * @return
	 */
	public boolean isDirty() {
		return this.isDirty;
	}
	
	private DisclosureSettingsForAddress findAddressDisclosure(Type addressType, String affiliation) {
		for (DisclosureSettingsForAddress ad : addressDsiclosureSettings) {
			if (ad.matchesTypeAndAffiliation(addressType, affiliation)) {
				return ad;
			}
		}
		return null;
	}
	
	private DisclosureSettingsForEmail findEmailDisclosure(Type addressType, String affiliation) {
		for (DisclosureSettingsForEmail ed : emailDsiclosureSettings) {
			if (ed.matchesTypeAndAffiliation(addressType, affiliation)) {
				return ed;
			}
		}
		return null;
	}
	
	private DisclosureSettingsForPhone findPhoneDisclosure(Type addressType, Type phoneType, String affiliation) {
		for (DisclosureSettingsForPhone pd : phoneDsiclosureSettings) {
			if (pd.matchesTypeAndAffiliation(addressType, phoneType, affiliation)) {
				return pd;
			}
		}
		return null;
	}
	
	private DisclosureSettingsForUrl findUrlDisclosure(Type addressType, String affiliation) {
		for (DisclosureSettingsForUrl ud : urlDsiclosureSettings) {
			if (ud.matchesTypeAndAffiliation(addressType, affiliation)) {
				return ud;
			}
		}
		return null;
	}
}
