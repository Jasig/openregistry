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
import org.openregistry.core.domain.DisclosureSettingsForUrl;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.service.DisclosureRecalculationStrategy;
import org.springframework.util.Assert;

@javax.persistence.Entity(name="JpaDisclosureSettingsForUrlImpl")
@Table(name="prc_disclosure_url", uniqueConstraints= @UniqueConstraint(columnNames={"address_t", "affiliation_t", "disclosure_record_id"}))
@Audited
public class JpaDisclosureSettingsForUrlImpl 
	extends Entity implements DisclosureSettingsForUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_disclosure_url_seq")
    @SequenceGenerator(name = "prc_disclosure_url_seq", sequenceName = "prc_disclosure_url_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="disclosure_record_id")
    private JpaDisclosureSettingsImpl disclosureRecord;

    @ManyToOne
    @JoinColumn(name="affiliation_t")
    @AllowedTypes(property = "affiliation.type")
    @NotNull
    private JpaTypeImpl affiliationType;

    @ManyToOne
    @JoinColumn(name="address_t")
    @AllowedTypes(property = "address.type")
    @NotNull
    private JpaTypeImpl type;

    @Column(name="public_ind", nullable=false)
    private boolean publicInd = true;

    @Column(name="public_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date publicDate;

    /**
     * Default constructor
     */
    public JpaDisclosureSettingsForUrlImpl () {}
    
    /**
     * Initializing constructor
     * @param role
     */
    public JpaDisclosureSettingsForUrlImpl (JpaDisclosureSettingsImpl disclosure, Type type, Type affType) {    	
    	Assert.notNull(disclosure);
    	Assert.isInstanceOf(JpaTypeImpl.class, type);
    	Assert.isInstanceOf(JpaTypeImpl.class, affType);
    	this.disclosureRecord = disclosure;
    	this.type = (JpaTypeImpl)type;
    	this.affiliationType = (JpaTypeImpl)affType;
    }
    
	public boolean getPublicInd() {
		return this.publicInd;
	}

	public Date getPublicIndDate() {
		return this.publicDate;
	}

	public Type getType() {
		return this.type;
	}
	
	public String getAffiliation() {
		return this.affiliationType.getDescription();
	}

	public Long getId() {
		return this.id;
	}

	public void setPublicInd(boolean isPublic) {
		this.publicInd = isPublic;
		this.publicDate = new Date();
	}

	public void setPublicInd(boolean isPublic, Date updateDate) {
		this.publicInd = isPublic;
		if (updateDate != null) {
			this.publicDate = new Date(updateDate.getTime());
		} else {
			this.publicDate = new Date();
		}
	}

	public boolean matchesTypeAndAffiliation(Type type, String affiliation) {
		Assert.notNull(type);
		Assert.notNull(affiliation);
		return this.type.isSameAs(type) && this.affiliationType.getDescription().equals(affiliation);
	}

	/**
	 * @see org.openregistry.core.domain.DisclosureSettingsForUrl#recalculate(java.lang.String, org.openregistry.core.service.DisclosureRecalculationStrategy)
	 */
	public void recalculate(
			String disclosureCode,
			DisclosureRecalculationStrategy disclosureRecalculationStrategy) {
		setPublicInd(this.publicInd &&
			disclosureRecalculationStrategy.isUrlPublic
			(disclosureCode, this.type.getDescription(), this.affiliationType.getDescription()), this.publicDate);
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
