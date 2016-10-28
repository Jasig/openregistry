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

import org.openregistry.core.domain.internal.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.sor.*;

import java.util.*;

/*
 * Created by IntelliJ IDEA.
 * User: rosey
 * Date: Oct 15, 2009
 * Time: 1:47:09 PM
 */

public class MockRole extends Entity implements Role {

	private Long id;

	private Type terminationReason;

	private Long sorRoleId;

    private Date end;

    private OrganizationalUnit organizationalUnit;

    private String title;

    private Type affiliationType;
    
    private Set <EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    private Set <Phone> phones = new HashSet<Phone>();

    private Type sponsorType;

    public MockRole(final SorRole sorRole) {
        this.sorRoleId = sorRole.getId();
        this.end = sorRole.getEnd();
        this.terminationReason = sorRole.getTerminationReason();
        for (EmailAddress ea : sorRole.getEmailAddresses()) {
        	this.addEmailAddress(ea);
         }
        for (Phone phone : sorRole.getPhones()) {
        	this.addPhone(phone);
         }
        this.affiliationType=sorRole.getAffiliationType();
        
        this.title = sorRole.getTitle();
    }

	public MockRole(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public int getPercentage() {
		return 0;
	}

	public Type getPersonStatus() {
		return null;
	}

	public void setSponsorId(Long id) {

	}

	public Long getSponsorId() {
		return 1L;
	}

    public void setSponsorType(Type type) {
        this.sponsorType = type;
	}

	public Type getSponsorType() {
		return sponsorType;
	}

	public Type getTerminationReason() {
		return terminationReason;
	}

	public boolean isTerminated() {
		return false;
	}

    @Override
    public boolean isNotYetActive() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public int compareTo(Role o) {
        return 0;
    }

    public void setSorRoleId(Long sorRoleId) {
		this.sorRoleId = sorRoleId;
	}

	public Long getSorRoleId() {
		return sorRoleId;
	}

    @Override
    public String getPHI() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPHI(String PHI) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getRBHS() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setRBHS(String RBHS) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<Address> getAddresses() {
		return null;
	}

	public Address addAddress(Address sorAddress) {
		return null;
	}

	public Set<Phone> getPhones() {
		return this.phones;
	}

	public Set<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public EmailAddress addEmailAddress() {
		return null;
	}

	public EmailAddress addEmailAddress(EmailAddress sorEmailAddress) {
		MockEmailAddress ea = new MockEmailAddress();
		ea.setAddress(sorEmailAddress.getAddress());
		ea.setAddressType(sorEmailAddress.getAddressType());
		emailAddresses.add(ea);
		return ea;
	}


	public Phone addPhone(Phone sorPhone) {
		MockPhoneNumber phone = new MockPhoneNumber();
        phone.setCountryCode(sorPhone.getCountryCode());
		phone.setAreaCode(sorPhone.getAreaCode());
		phone.setNumber(sorPhone.getNumber());
        phone.setExtension(sorPhone.getExtension());
		phones.add(phone);
        return phone;
	}


	public Set<Url> getUrls() {
		return null;
	}

	public Url addUrl(Url sorUrl) {
		return null;
	}

	public Set<Leave> getLeaves() {
		return null;
	}

	public String getTitle() {
		return title;
	}

    public void setTitle(String title){
        this.title = title;
    }

	public OrganizationalUnit getOrganizationalUnit() {
		return null;
	}

    public void setOrganizationalUnit(OrganizationalUnit unit) {
		this.organizationalUnit = unit;
	}

	public Type getAffiliationType() {
		return this.affiliationType;
	}

    public void setAffiliationType(Type affiliationType){
        this.affiliationType = affiliationType;
    }

	public String getDisplayableName() {
		return null;
	}

	public Date getStart() {
		return null;
	}

	public Date getEnd() {
		return this.end;
	}

    public void expireNow(final Type terminationReason, final boolean orphaned) {
        this.terminationReason = terminationReason;
        this.end = new Date();

        if (orphaned) {
            this.sorRoleId = null;
        }
    }

    public void recalculate(final SorRole sorRole) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MockRole mockRole = (MockRole) o;

        if (end != null ? !end.equals(mockRole.end) : mockRole.end != null) return false;
        if (id != null ? !id.equals(mockRole.id) : mockRole.id != null) return false;
        if (sorRoleId != null ? !sorRoleId.equals(mockRole.sorRoleId) : mockRole.sorRoleId != null) return false;
        if (terminationReason != null ? !terminationReason.equals(mockRole.terminationReason) : mockRole.terminationReason != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (terminationReason != null ? terminationReason.hashCode() : 0);
        result = 31 * result + (sorRoleId != null ? sorRoleId.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    public SystemOfRecord getSystemOfRecord() {
        return null;
    }
}