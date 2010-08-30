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

import org.hibernate.annotations.*;
import org.openregistry.core.domain.annotation.Gender;
import org.openregistry.core.domain.annotation.Required;
import org.openregistry.core.domain.annotation.RequiredSize;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.DisclosureSettings;
import org.openregistry.core.domain.Name;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaDisclosureSettingsImpl;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.*;

/**
 * Implementation of the SoR Person.
 * Unique constraints assumes each person can have only one sorPerson from any given SOR
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name = "sorPerson")
@Table(name = "prs_sor_persons", uniqueConstraints = @UniqueConstraint(columnNames = {"source_sor_id", "person_id"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "prs_sor_persons", indexes = {@Index(name = "SOR_PERSON_SOURCE_AND_ID_INDEX", columnNames = {"source_sor_id", "id"})})
public class JpaSorPersonImpl extends Entity implements SorPerson {

    protected static final Logger logger = LoggerFactory.getLogger(JpaSorPersonImpl.class);

    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sor_persons_seq")
    @SequenceGenerator(name = "prs_sor_persons_seq", sequenceName = "prs_sor_persons_seq", initialValue = 1, allocationSize = 50)
    private Long recordId;

    @Column(name = "id")
    @Required(property = "person.sorId")
    private String sorId;

    @Column(name = "source_sor_id", nullable = false)
    @NotNull
    @Size(min = 1)
    private String sourceSor;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    @Required(property = "person.dateOfBirth", message = "dateOfBirthRequiredMsg")
    @Past
    private Date dateOfBirth;

    @Column(name = "gender", length = 1, nullable = true)
    @Required(property = "person.gender", message = "{genderRequiredMsg}")
    @Size(min = 1, max = 1, message = "genderRequiredMsg")
    @Gender
    private String gender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.EAGER, targetEntity = JpaSorNameImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Fetch(value = FetchMode.SUBSELECT)
    @RequiredSize(property = "person.names")
    @Valid
    private List<SorName> names = new ArrayList<SorName>();

    @Column(name = "ssn", nullable = true, length = 9)
    @Required(property = "person.ssn")
    private String ssn;

    @OneToOne(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER, targetEntity = JpaSorDisclosureSettingsImpl.class)
    private JpaSorDisclosureSettingsImpl disclosureSettings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.EAGER, targetEntity = JpaSorRoleImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Fetch(value = FetchMode.SUBSELECT)
    @Valid
    private List<SorRole> roles = new ArrayList<SorRole>();

    @ElementCollection
    @CollectionTable(name = "prs_sor_persons_loc_attr", joinColumns = @JoinColumn(name = "sor_person_record_id"))
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "attribute_value")
    private Map<String, String> sorLocalAttributes = new HashMap<String, String>();

    public List<SorRole> getRoles() {
        return this.roles;
    }

    public String getSsn() {
        return this.ssn;
    }

    public void setSsn(final String ssn) {
        this.ssn = ssn;
    }

    public Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public String getSourceSor() {
        return this.sourceSor;
    }

    public void setSourceSor(final String sorIdentifier) {
        this.sourceSor = sorIdentifier;
    }

    public List<SorName> getNames() {
        return this.names;
    }

    public void setSorId(final String id) {
        this.sorId = id;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final Date date) {
        this.dateOfBirth = date;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    /**
     * @see org.openregistry.core.domain.sor.SorPerson#getDisclosureSettings()
     */
    public DisclosureSettings getDisclosureSettings() {
		return this.disclosureSettings;
	}

	/**
	 * @see org.openregistry.core.domain.sor.SorPerson#setDisclosureSettings(org.openregistry.core.domain.DisclosureSettings)
	 */
	public void setDisclosureSettings(DisclosureSettings ds) {
		if (ds instanceof JpaSorDisclosureSettingsImpl) {
			this.disclosureSettings = new JpaSorDisclosureSettingsImpl
				(this, ds.getDisclosureCode(), ds.getLastUpdateDate(), ds.isWithinGracePeriod());
		} else {
			throw new IllegalArgumentException("Disclosure settings parameter must be of type JpaDisclosureSettingsImpl");
		}
	}


	/**
	 * @see org.openregistry.core.domain.sor.SorPerson#setDisclosureSettingInfo(java.lang.String, boolean, java.util.Date)
	 */
	public void setDisclosureSettingInfo(String disclosureCode,
			boolean isWithinGracePeriod, Date lastUpdatedDate) {
		this.disclosureSettings = new JpaSorDisclosureSettingsImpl
			(this, disclosureCode, lastUpdatedDate, isWithinGracePeriod);
	}
	
    public SorName addName() {
        final JpaSorNameImpl jpaSorName = new JpaSorNameImpl(this);
        this.names.add(jpaSorName);
        return jpaSorName;
    }

    public SorName addName(Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        final JpaSorNameImpl jpaSorName = new JpaSorNameImpl(this);
        jpaSorName.setType(type);
        this.names.add(jpaSorName);
        return jpaSorName;
    }

    public synchronized SorName findNameByNameId(final Long id) {
        for (final SorName name : this.names) {
            final Long nameId = name.getId();
            if (nameId != null && nameId.equals(id)) {
                return name;
            }
        }
        return null;
    }

    public String getFormattedName() {
        if (!this.names.isEmpty()) {
            return this.names.get(0).getFormattedName();
        }

        throw new IllegalStateException("No names found!");
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public SorRole addRole(final RoleInfo roleInfo) {
        Assert.isInstanceOf(JpaRoleInfoImpl.class, roleInfo);
        final JpaSorRoleImpl jpaRole = new JpaSorRoleImpl((JpaRoleInfoImpl) roleInfo, this);
        this.roles.add(jpaRole);
        return jpaRole;
    }

    public SorRole pickOutRole(String code) {
        //TODO: Is this the correct assumption???
        for (SorRole r : this.roles) {
            if (r.getRoleInfo().getCode().equals(code)) {
                return r;
            }
        }
        return null;
    }

    public SorRole findSorRoleBySorRoleId(final String sorRoleId) {
        Assert.notNull(sorRoleId);
        for (final SorRole role : this.roles) {
            if (sorRoleId.equals(role.getSorId())) {
                return role;
            }
        }
        return null;
    }

    @Override
    public SorRole findSorRoleById(final Long roleId) {
        Assert.notNull(roleId);

        for (final SorRole role : this.roles) {
            if (roleId.equals(role.getId())) {
                return role;
            }
        }
        return null;
    }

    public Map<String, String> getSorLocalAttributes() {
        return this.sorLocalAttributes;
    }

    @Override
    public List<SorRole> findOpenRolesByAffiliation(Type affiliationType) {
        List<SorRole> rolesToReturn = new ArrayList<SorRole>();
        for(SorRole r : this.roles) {
            if(r.getAffiliationType().isSameAs(affiliationType) && r.isActive()) {
                rolesToReturn.add(r);
            }
        }
        return rolesToReturn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaSorPersonImpl)) return false;

        final JpaSorPersonImpl that = (JpaSorPersonImpl) o;

        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (names != null ? !names.equals(that.names) : that.names != null) return false;
        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (recordId != null ? !recordId.equals(that.recordId) : that.recordId != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;
        if (sorId != null ? !sorId.equals(that.sorId) : that.sorId != null) return false;
        if (sourceSor != null ? !sourceSor.equals(that.sourceSor) : that.sourceSor != null)
            return false;
        if (ssn != null ? !ssn.equals(that.ssn) : that.ssn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recordId != null ? recordId.hashCode() : 0;
        result = 31 * result + (sorId != null ? sorId.hashCode() : 0);
        result = 31 * result + (sourceSor != null ? sourceSor.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (names != null ? names.hashCode() : 0);
        result = 31 * result + (ssn != null ? ssn.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}
