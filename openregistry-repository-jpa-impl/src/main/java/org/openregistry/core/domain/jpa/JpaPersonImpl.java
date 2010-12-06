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

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.sor.JpaSorDisclosureSettingsImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorNameImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.*;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.*;

/**
 * Person entity mapped to a persistence store with JPA annotations.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
@javax.persistence.Entity(name="person")
@Table(name="prc_persons")
@Audited
public class JpaPersonImpl extends Entity implements Person {

    protected static final Logger logger = LoggerFactory.getLogger(JpaPersonImpl.class);

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_persons_seq")
    @SequenceGenerator(name="prc_persons_seq",sequenceName="prc_persons_seq",initialValue=1,allocationSize=50)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<JpaNameImpl> names = new HashSet<JpaNameImpl>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER, targetEntity = JpaRoleImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Role> roles = new ArrayList<Role>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER)
    private Set<JpaIdentifierImpl> identifiers = new HashSet<JpaIdentifierImpl>();

    @Column(name="date_of_birth",nullable=true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name="gender",length=1,nullable=true)
    private String gender;

    @OneToOne(cascade=CascadeType.ALL, mappedBy="person", fetch = FetchType.EAGER, targetEntity = JpaDisclosureSettingsImpl.class)
    private JpaDisclosureSettingsImpl disclosureSettings;
    
    @Embedded
    private JpaActivationKeyImpl activationKey = new JpaActivationKeyImpl();

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_email_id")
    private JpaContactEmailAddressImpl emailAddress = new JpaContactEmailAddressImpl();

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_phone_id")
    private JpaContactPhoneImpl phoneNumber = new JpaContactPhoneImpl();

    public Long getId() {
        return this.id;
    }

    @Override
    public ContactEmailAddress getPreferredContactEmailAddress() {
        return this.emailAddress;
    }

    @Override
    public ContactPhone getPreferredContactPhoneNumber() {
        return this.phoneNumber;
    }

    public Set<? extends Name> getNames() {
    	return this.names;
    }

    public void addName(final SorName sorName) {
        Assert.isInstanceOf(JpaSorNameImpl.class, sorName);
    	final JpaNameImpl name = new JpaNameImpl(this);
        // TODO we should probably use a constructor, but the important thing is these setters are not exposed.
    	name.setType(sorName.getType());
        name.setGiven(sorName.getGiven());
        name.setFamily(sorName.getFamily());
        name.setMiddle(sorName.getMiddle());
        name.setPrefix(sorName.getPrefix());
        name.setSuffix(sorName.getSuffix());
        name.setSourceNameId(sorName.getId());
    	this.names.add(name);
    }

    public Name getOfficialName() {
    	Set<? extends Name> names = this.getNames();
    	for(Name name: names) {
    		if (name.isOfficialName()) {
    			return name;
    		}
    	}
    	return null;
    }

    //TODO really don't need this.  Should replace code with getOfficialName().getFormattedName()
    public String getFormattedName(){
        return this.getOfficialName().getFormattedName();
    }

    public Name getPreferredName() {
       	Set<? extends Name> names = this.getNames();
    	for(final Name name: names) {
    		if (name.isPreferredName()) {
    			return name;
    		}
    	}
    	return null;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * @see org.openregistry.core.domain.Person#getDisclosureSettings()
     */
	public DisclosureSettings getDisclosureSettings() {
		return this.disclosureSettings;
	}

	public void setGender(String gender){
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @see org.openregistry.core.domain.Person#setDisclosureSettings(org.openregistry.core.domain.DisclosureSettings)
     */
	public void setDisclosureSettings(DisclosureSettings ds) {
		if (ds != null) {
			if (ds instanceof JpaDisclosureSettingsImpl || 
				ds instanceof JpaSorDisclosureSettingsImpl) {
				// copy and convert from JpaDisclosureSettingsImpl to JpaSorDisclosureSettingsImpl if necessary
				this.disclosureSettings = new JpaDisclosureSettingsImpl (
					this,
					ds.getDisclosureCode(),
					ds.getLastUpdateDate(),
					ds.isWithinGracePeriod()
					);
			} else {
				throw new IllegalArgumentException("Disclosure settings parameter must be of type JpaDisclosureSettingsImpl or JpaSorDisclosureSettingsImpl");
			}
		} else {
			this.disclosureSettings = null;
		}
	}

	/**
	 * @see org.openregistry.core.domain.sor.SorPerson#setDisclosureSettingInfo(java.lang.String, boolean, java.util.Date)
	 */
	public void setDisclosureSettingInfo(String disclosureCode,
			boolean isWithinGracePeriod, Date lastUpdatedDate) {
		this.disclosureSettings = new JpaDisclosureSettingsImpl
			(this, disclosureCode, lastUpdatedDate, isWithinGracePeriod);
	}
	
    public Role addRole(final SorRole sorRole) {
        Assert.isInstanceOf(JpaSorRoleImpl.class, sorRole);
        final JpaRoleImpl jpaRole = new JpaRoleImpl((JpaSorRoleImpl) sorRole, this);
        this.roles.add(jpaRole);
        return jpaRole;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public Set<? extends Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public Identifier addIdentifier(final IdentifierType identifierType, final String value) {
        Assert.isInstanceOf(JpaIdentifierTypeImpl.class, identifierType);
        final JpaIdentifierImpl jpaIdentifier = new JpaIdentifierImpl(this, (JpaIdentifierTypeImpl) identifierType, value);
        this.identifiers.add(jpaIdentifier);
        return jpaIdentifier;
    }

	public void setIdentifierNotified(IdentifierType identifierType, Date date) 
		throws IllegalStateException, IllegalArgumentException {

		if (identifierType == null) {
			throw new IllegalArgumentException("Identifier type must be supplied");
		}
		
		Identifier identiferToUpdate = this.getPrimaryIdentifiersByType().get(identifierType.getName());
		if (identiferToUpdate != null && identiferToUpdate.getNotificationDate() == null) {
			identiferToUpdate.setNotificationDate(date);
		} else {
			throw new IllegalStateException("Identifier to be updated was not found");
		}
	}
	
    public Role pickOutRole(Type affiliationType) {
        //TODO: Is this the correct assumption???
        for(final Role r : this.roles) {
            if(r.getAffiliationType().getDescription().equals(affiliationType.getDescription())) {
                return r;
            }
        }
        return null;
    }

    public Role pickOutRole(String affiliation) {
        for(final Role r : this.roles) {
            if(r.getAffiliationType().getDescription().equals(affiliation)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public Map<String, Identifier> getPrimaryIdentifiersByType() {
        final Map<String, Identifier> primaryIdentifiers = new HashMap<String,Identifier>();

        for (final Identifier identifier : this.identifiers) {
            if (identifier.isPrimary()) {
                primaryIdentifiers.put(identifier.getType().getName(), identifier);
            }
        }

        return primaryIdentifiers;
    }

    @Override
    public Map<String, Deque<Identifier>> getIdentifiersByType() {
        final Map<String, Deque<Identifier>> identifiersByType = new HashMap<String, Deque<Identifier>>();

        for (final Identifier identifier : this.identifiers) {
            final String identifierType = identifier.getType().getName();
            Deque<Identifier> listIdentifiers = identifiersByType.get(identifierType);

            if (listIdentifiers == null) {
                listIdentifiers = new ArrayDeque<Identifier>();
                identifiersByType.put(identifierType, listIdentifiers);
            }

            if (identifier.isPrimary()) {
                listIdentifiers.addFirst(identifier);
            } else {
                listIdentifiers.addLast(identifier);
            }
        }

        return identifiersByType;
    }

    public synchronized ActivationKey generateNewActivationKey(final Date start, final Date end) {
        this.activationKey = new JpaActivationKeyImpl(start, end);
        return this.activationKey;
    }

    public synchronized ActivationKey generateNewActivationKey(final Date end) {
        return generateNewActivationKey(new Date(), end);
    }

    public synchronized ActivationKey getCurrentActivationKey(){
        return this.activationKey != null && this.activationKey.isInitialized() ? this.activationKey : null;
    }

    public synchronized void removeCurrentActivationKey() {
        this.activationKey.removeKeyValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JpaPersonImpl jpaPerson = (JpaPersonImpl) o;

        if (activationKey != null ? !activationKey.equals(jpaPerson.activationKey) : jpaPerson.activationKey != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(jpaPerson.dateOfBirth) : jpaPerson.dateOfBirth != null)
            return false;
        if (gender != null ? !gender.equals(jpaPerson.gender) : jpaPerson.gender != null) return false;
        if (id != null ? !id.equals(jpaPerson.id) : jpaPerson.id != null) return false;
        if (identifiers != null ? !identifiers.equals(jpaPerson.identifiers) : jpaPerson.identifiers != null)
            return false;
        if (names != null ? !names.equals(jpaPerson.names) : jpaPerson.names != null) return false;
        if (roles != null ? !roles.equals(jpaPerson.roles) : jpaPerson.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (names != null ? names.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (identifiers != null ? identifiers.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (activationKey != null ? activationKey.hashCode() : 0);
        return result;
    }

    public Role findRoleBySoRRoleId(final Long sorRoleId) {
        Assert.notNull(sorRoleId);
        for (final Role role : this.roles) {
            if (sorRoleId.equals(role.getSorRoleId())) {
                return role;
            }
        }
        return null;
    }
}
