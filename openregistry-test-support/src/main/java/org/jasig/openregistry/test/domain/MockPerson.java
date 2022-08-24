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

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.SorDisclosureSettings;
import org.openregistry.core.domain.sor.SorName;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.service.DisclosureRecalculationStrategy;

import java.util.*;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockPerson implements Person {

    private ActivationKey activationKey = new MockActivationKey(UUID.randomUUID().toString(), new Date(), new Date());

	private Set<Role> roles = new HashSet<Role>();

    private long id = 1L;

    private Set<MockName> names = new HashSet<MockName>();

    private MockEmailAddress mockEmailAddress = new MockEmailAddress();

    private MockPhoneNumber mockPhoneNumber = new MockPhoneNumber();
    
    private MockDisclosureSettings mockDisclosureSettings;
    
    private final Set<Identifier> identifiers = new HashSet<Identifier>();
    private final Set<IdCard> idcards = new HashSet<IdCard>();

    private Map<String, String> attributes = new HashMap<String, String>();


    private String gender;

    private Date dob;

    /**
     * Generates a new active, non-expired person with Identifier of type NETID
     */
    public MockPerson() {
        this("testId", false, false);
    }

    /**
     * Generates a person with Identifier of type NETID with supplied value
     */
    public MockPerson(final String identifierValue, final boolean notActive, final boolean expired) {

        if (notActive && expired) {
            throw new IllegalArgumentException("You're crazy!");
        }

        final Date startDate;
        final Date endDate;

        if (notActive) {
            startDate = new Date(System.currentTimeMillis() + 50000);
            endDate = new Date(System.currentTimeMillis() + 50000000);
        } else if (expired) {
            startDate = new Date(System.currentTimeMillis() - 500000);
            endDate = new Date(System.currentTimeMillis() - 50000);
        } else {
            startDate = new Date();
            endDate = new Date(System.currentTimeMillis() + 50000000);
        }

        this.activationKey = new MockActivationKey("key", startDate, endDate);
        this.addIdentifier(new MockIdentifierType("NETID", false), identifierValue);
    }

    public MockPerson(long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public ContactEmailAddress getPreferredContactEmailAddress() {
        return this.mockEmailAddress;
    }

    @Override
    public ContactPhone getPreferredContactPhoneNumber() {
        return this.mockPhoneNumber;
    }

    @Override
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void addRole(final Role role) {
        this.roles.add(role);
    }

    public Set<? extends Name> getNames() {
        return this.names;
    }

    public Name addName() {
        final MockName name = new MockName();
        this.names.add(name);
        return name;
    }

    public Name addName(Type type) {
        final MockName name = new MockName(type);
        this.names.add(name);
        return name;
    }

    @Override
    public void addName(final SorName sorName) {
        final MockName name = new MockName();
        name.setType(sorName.getType());
        name.setGiven(sorName.getGiven());
        name.setFamily(sorName.getFamily());
        name.setMiddle(sorName.getMiddle());
        name.setPrefix(sorName.getPrefix());
        name.setSuffix(sorName.getSuffix());
        name.setSourceNameId(sorName.getId());
        this.names.add(name);

        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public Role addRole(final SorRole sorRole) {
        final MockRole mockRole = new MockRole(sorRole);
        this.roles.add(mockRole);
        return mockRole;
    }

    public Set<Identifier> getIdentifiers() {
       return identifiers;
    }

    @Override
    public Set<IdCard> getIdCards() {
        return idcards;
    }


    public Name getPreferredName() {
        return null;
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

    public Name getUniversityName() {
        Set<? extends Name> names = this.getNames();
        for(Name name: names) {
            if (name.isOfficialName()) {
                return name;
            }
        }
        return null;
    }

    public Name getChosenName() {return null;}

    public String getGender() {
        return this.gender;
    }

    public Date getDateOfBirth() {
        return this.dob;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dob = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public void calculateDisclosureSettings(SorDisclosureSettings ds) {
    	if (ds != null) {
    		this.mockDisclosureSettings = 
    			new MockDisclosureSettings(ds.getDisclosureCode(), ds.getLastUpdateDate(), ds.isWithinGracePeriod());
    	} else {
    		this.mockDisclosureSettings = null;
    	}
    }
	public DisclosureSettings getDisclosureSettings() {
		return this.mockDisclosureSettings;
	}

	public void setDisclosureSettingInfo(String disclosureCode,
			boolean isWithinGracePeriod, Date lastUpdatedDate) {
		this.mockDisclosureSettings = new MockDisclosureSettings
			(disclosureCode, lastUpdatedDate, isWithinGracePeriod);
	}
   
    public Identifier addIdentifier(IdentifierType identifierType, String value) {
    	MockIdentifier mid = new MockIdentifier(this, identifierType, value);
    	this.identifiers.add(mid);
    	return mid; 
    }
    public IdCard addIDCard( String cardNumber,String cardSecurityValue, String barCode){
        MockIdCard card =new MockIdCard(this,cardNumber,cardSecurityValue,barCode);
        this.idcards.add(card);
        return card;
    }

    @Override
    public IdCard addIDCard(IdCard idCard) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void setIdentifierNotified(IdentifierType identifierType, Date date) {
		if (!identifierType.isNotifiable()) {
			throw new IllegalArgumentException("Only notifiable identifiers can have a notification date set");
		}
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
	
    public Name addOfficialName() {
        MockName name = new MockName();
        name.setOfficialName(true);
        return name;
    }

    public Name addPreferredName() {
        MockName name = new MockName();
        name.setPreferredName(true);
        return name;
    }

    public void setPreferredName(Name name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Role pickOutRole(Type affiliationType) {
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
        final Map<String,Identifier> primaryIdentifiers = new HashMap<String,Identifier>();

        for (final Identifier identifier : getIdentifiers()) {
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

    public ActivationKey generateNewActivationKey(Date start, Date end) {
        this.activationKey = new MockActivationKey(UUID.randomUUID().toString(), start, end);
        return this.activationKey;
    }

    public ActivationKey generateNewActivationKey(Date end) {
        this.activationKey = new MockActivationKey(UUID.randomUUID().toString(), new Date(), end);
        return this.activationKey;
    }

    public ActivationKey getCurrentActivationKey() {
        return this.activationKey;
    }

    public void removeCurrentActivationKey() {
        this.activationKey = null;
    }

    public Role findRoleBySoRRoleId(final Long sorRoleId) {
        for (final Role role : this.roles) {
            if (sorRoleId.equals(role.getSorRoleId())) {
                return role;
            }
        }
        return null;
    }

    @Override
    public Identifier findIdentifierByValue(String identifierType, String identifierValue) {
        final Map<String, Deque<Identifier>> identifiersByType = getIdentifiersByType();
        Deque<Identifier> ids = identifiersByType.get(identifierType);
        if(ids == null) {
             return null;
        }
        Iterator<Identifier> iter = ids.iterator();
        Identifier id = null;
        while(iter.hasNext()) {
            id = iter.next();
            if(id.getValue().equals(identifierValue)) {
                return id;
            }
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MockPerson that = (MockPerson) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return 31 * (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "MockPerson{" +
                "activationKey=" + activationKey +
                ", roles=" + roles +
                ", id=" + id +
                ", names=" + names +
                ", mockEmailAddress=" + mockEmailAddress +
                ", mockPhoneNumber=" + mockPhoneNumber +
                ", mockDisclosureSettings=" + mockDisclosureSettings +
                ", identifiers=" + identifiers +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                '}';
    }

    @Override
    public IdCard getPrimaryIdCard() {
        if(this.idcards.size()==0) return null;

        for(IdCard card:idcards){
            if (card.isPrimary())
                return card;
        }

        return null;
    }
    public void setAttributes(Map<String, String> attributes){
        this.attributes=attributes;
    }
}
