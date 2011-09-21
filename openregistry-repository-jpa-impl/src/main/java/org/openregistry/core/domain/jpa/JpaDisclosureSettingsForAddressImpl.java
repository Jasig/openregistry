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
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.DisclosureSettingsForAddress;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.DisclosureSettings.PropertyNames;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.service.DisclosureRecalculationStrategy;
import org.springframework.util.Assert;

@javax.persistence.Entity(name="disclosureSettingsForAddress")
@Table(name="prc_disclosure_address", uniqueConstraints= @UniqueConstraint(columnNames={"address_t", "affiliation_t", "disclosure_record_id"}))
@Audited
public class JpaDisclosureSettingsForAddressImpl 
	extends Entity implements DisclosureSettingsForAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_disclosure_address_seq")
    @SequenceGenerator(name = "prc_disclosure_address_seq", sequenceName = "prc_disclosure_address_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="disclosure_record_id")
    private JpaDisclosureSettingsImpl disclosureRecord;

    @ManyToOne
    @JoinColumn(name="address_t")
    @AllowedTypes(property = "address.type")
    @NotNull
    private JpaTypeImpl addressType;

    @ManyToOne
    @JoinColumn(name="affiliation_t")
    @AllowedTypes(property = "affiliation.type")
    @NotNull
    private JpaTypeImpl affiliationType;

    @Column(name="address_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date addressLinesDate;

    @Column(name="address_ind", nullable=false)
    private boolean addressLinesPublic = true;

    @Column(name="bldg_room_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date addressBuildingDate;

    @Column(name="bldg_room_ind", nullable=false)
    private boolean addressBuildingPublic = true;

    @Column(name="city_region_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date addressRegionDate;

    @Column(name="city_region_ind", nullable=false)
    private boolean addressRegionPublic = true;

    /**
     * Default constructor
     */
    public JpaDisclosureSettingsForAddressImpl () {}
    
    /**
     * Initializing constructor
     * @param role
     */
    public JpaDisclosureSettingsForAddressImpl 
    	(JpaDisclosureSettingsImpl disclosure, Type addressType, Type affType) {    	
    	Assert.notNull(disclosure);
    	Assert.isInstanceOf(JpaTypeImpl.class, addressType);
    	Assert.isInstanceOf(JpaTypeImpl.class, affType);
    	this.disclosureRecord = disclosure;
    	this.addressType = (JpaTypeImpl)addressType;
    	this.affiliationType = (JpaTypeImpl)affType;
    }
    
    public void initFromProperties(Map<DisclosureSettings.PropertyNames, Object> properties) {
    	if (properties.containsKey(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND)) {
    		this.addressLinesPublic = ((Boolean)properties.get(DisclosureSettings.PropertyNames.ADDRESS_LINES_IND)).booleanValue();
    		this.addressLinesDate = (properties.get(DisclosureSettings.PropertyNames.ADDRESS_LINES_DATE) != null) ?
    			new Date(((Date)properties.get(DisclosureSettings.PropertyNames.ADDRESS_LINES_DATE)).getTime()) : new Date();
    	} else if (this.addressLinesDate == null) {
    		this.addressLinesDate = new Date();
    	}
    	if (properties.containsKey(DisclosureSettings.PropertyNames.BUILDING_IND)) {
    		this.addressBuildingPublic = ((Boolean)properties.get(DisclosureSettings.PropertyNames.BUILDING_IND)).booleanValue();
    		this.addressBuildingDate = (properties.get(DisclosureSettings.PropertyNames.BUILDING_DATE) != null) ?
        		new Date(((Date)properties.get(DisclosureSettings.PropertyNames.BUILDING_DATE)).getTime()) : new Date();
    	} else if (this.addressBuildingDate == null) {
    		this.addressBuildingDate = new Date();
    	}
    	if (properties.containsKey(DisclosureSettings.PropertyNames.REGION_IND)) {
    		this.addressRegionPublic = ((Boolean)properties.get(DisclosureSettings.PropertyNames.REGION_IND)).booleanValue();
    		this.addressRegionDate = (properties.get(DisclosureSettings.PropertyNames.REGION_DATE) != null) ?
            	new Date(((Date)properties.get(DisclosureSettings.PropertyNames.REGION_DATE)).getTime()) : new Date();
    	} else if (this.addressRegionDate == null) {
    		this.addressRegionDate = new Date();
    	}	
    }    
    
	public boolean getAddressBuildingPublicInd() {
		return this.addressBuildingPublic;
	}

	public Date getAddressBuildingPublicIndDate() {
		return (this.addressBuildingDate != null ) ?
			new Date(this.addressBuildingDate.getTime()) : null;
	}

	public boolean getAddressLinesPublicInd() {
		return this.addressLinesPublic;
	}

	public Date getAddressLinesPublicIndDate() {
		return (this.addressLinesDate != null ) ?
			new Date(this.addressLinesDate.getTime()) : null;
	}

	public boolean getAddressRegionPublicInd() {
		return this.addressRegionPublic;
	}

	public Date getAddressRegionPublicIndDate() {
		return (this.addressRegionDate != null ) ?
			new Date(this.addressRegionDate.getTime()) : null;
	}

	public Type getAddressType() {
		return this.addressType;
	}


	/**
	 * @see org.openregistry.core.domain.DisclosureSettingsForAddress#getAffiliation()
	 */
	public String getAffiliation() {
		return this.affiliationType.getDescription();
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettingsForAddress#matchesTypeAndAffiliation(org.openregistry.core.domain.Type, org.openregistry.core.domain.Type)
	 */
	public boolean matchesTypeAndAffiliation(Type addressType, String affiliation) {
		Assert.notNull(addressType);
		Assert.notNull(affiliation);
		return this.addressType.isSameAs(addressType) && this.affiliationType.getDescription().equals(affiliation);
	}
	
	/**
	 * @see org.openregistry.core.domain.internal.Entity#getId()
	 */
	protected Long getId() {
		return this.id;
	}
	
	/**
	 * @see org.openregistry.core.domain.DisclosureSettingsForAddress#recalculate(java.lang.String, org.openregistry.core.service.DisclosureRecalculationStrategy)
	 */
	public void recalculate(String disclosureCode, DisclosureRecalculationStrategy disclosureRecalculationStrategy) {
		Map<PropertyNames, Object> newValuesMap = new HashMap<PropertyNames, Object>();
		newValuesMap.put(PropertyNames.ADDRESS_LINES_IND,
			new Boolean(this.addressLinesPublic &&
				disclosureRecalculationStrategy.isAddressLinesPublic
				(disclosureCode, this.addressType.getDescription(), this.affiliationType.getDescription())));
		newValuesMap.put(PropertyNames.ADDRESS_LINES_DATE, this.addressLinesDate);
		newValuesMap.put(PropertyNames.BUILDING_IND,
			new Boolean(this.addressBuildingPublic &&
				disclosureRecalculationStrategy.isAddressBuildingPublic
				(disclosureCode, this.addressType.getDescription(), this.affiliationType.getDescription())));
		newValuesMap.put(PropertyNames.BUILDING_DATE, this.addressBuildingDate);
		newValuesMap.put(PropertyNames.REGION_IND,
			new Boolean(this.addressRegionPublic && 
				disclosureRecalculationStrategy.isAddressRegionPublic
				(disclosureCode, this.addressType.getDescription(), this.affiliationType.getDescription())));
		newValuesMap.put(PropertyNames.REGION_DATE, this.addressRegionDate);
		this.initFromProperties(newValuesMap);			
	}
    
	/** 
     * A listener that makes sure that the overall flag is in sync
     * with the individual flags before saving
     * Throws IllegalStateException if this object has unreconciled changes
     */
    @PrePersist
    @PreUpdate
    void preUpdate() {
		if (this.disclosureRecord.isDirty()) {
			throw new IllegalStateException("The overall disclosure setting may be inconsistent with field-level flags. Please call recalculate()");
		}
    }

}
