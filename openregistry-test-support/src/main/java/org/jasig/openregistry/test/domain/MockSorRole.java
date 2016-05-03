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
import org.openregistry.core.domain.internal.*;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SystemOfRecord;

import java.util.*;

/*
 * Created by IntelliJ IDEA.
 * User: rosey
 * Date: Oct 15, 2009
 * Time: 1:47:09 PM
 */

public class MockSorRole extends Entity implements SorRole {

	private Long id;

	private String sorId;

	private SystemOfRecord systemOfRecord;

	private Type terminationReason;
	
	private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    private List<Phone> phones = new ArrayList<Phone>();

    private Type sponsorType;

    private Type affiliationType;

    private OrganizationalUnit organizationalUnit;

    private String title;

    private Date end;

    private String PHI;

    private String RBHS;

	public MockSorRole() {
	}

    public MockSorRole(final Long id) {
        this.id = id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

	public Long getId() {
		return id;
	}

	public String getSorId() {
		return sorId;
	}

	public void setSorId(String id) {
		this.sorId = id;
	}

    public SystemOfRecord getSystemOfRecord() {
		return this.systemOfRecord;
	}

    public void setSystemOfRecord(SystemOfRecord systemOfRecord) {
        this.systemOfRecord = systemOfRecord;
    }

	public void expireNow(Type terminationReason) {
	}

	public void expire(Type terminationReason, Date expirationDate) {
	}

	public int getPercentage() {
		return 0;
	}

	public void setPercentage(int percentage) {
	}

	public Type getPersonStatus() {
		return null;
	}

	public void setPersonStatus(Type personStatus) {
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

	public void setTerminationReason(Type reason) {
		this.terminationReason = reason;
	}

    public String getPHI() {
        return PHI;
    }

    public void setPHI(String PHI) {
        this.PHI = PHI;
    }

    public String getRBHS() {
        return RBHS;
    }

    public void setRBHS(String RBHS) {
        this.RBHS = RBHS;
    }

    public List<Address> getAddresses() {
		return null;
	}

    public Address createAddress() {
		return null;
	}

	public void addAddress(Address address) {
	}

	public Address removeAddressById(Long id) {
		return null;
	}

	public Address getAddress(long id) {
		return null;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

    @Override
    public SorPerson getPerson() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EmailAddress removeEmailAddressById(Long id) {
		return null;
	}

	public EmailAddress createEmailAddress() {
        return new MockEmailAddress();
	}

    public void addEmailAddress(EmailAddress emailAddress) {
        this.emailAddresses.add(emailAddress);
	}

    public Phone createPhone() {
		return new MockPhoneNumber();
	}

	public void addPhone(Phone phone) {
        this.phones.add(phone);
	}

	public Phone removePhoneById(Long id) {
		return null;
	}

	public List<Url> getUrls() {
		return null;
	}

	public Url createUrl() {
		return null;
	}

    public void addUrl(Url url) {
	}

	public Url removeURLById(Long id) {
		return null;
	}

	public List<Leave> getLeaves() {
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

	public String getCode() {
		return null;
	}

	public String getDisplayableName() {
		return null;
	}

	public String getLocalCode() {
		return null;
	}

	public void setStart(Date date) {
	}

	public void setEnd(Date date) {
        this.end=date;
	}

	public Date getStart() {
		return null;
	}

	public Date getEnd() {
		return end;
	}

    @Override
    public void addOrUpdateEmail(String emailAddress, Type emailType) {
        boolean updated = false;
        for (EmailAddress e : this.emailAddresses) {
            if (e.getAddressType().isSameAs(emailType)) {
                e.setAddress(emailAddress);
                updated = true;
                break;
            }
        }
        if (!updated) {
            EmailAddress e = new MockEmailAddress();
            e.setAddressType(emailType);
            e.setAddress(emailAddress);
            this.emailAddresses.add(e);
        }
   }

    @Override
    public boolean isTerminated() {
        return true;
    }

    @Override
    public boolean isNotYetActive() {
        throw new UnsupportedOperationException("This mock does not implement this method yet");
    }

    @Override
    public boolean isActive() {
        throw new UnsupportedOperationException("This mock does not implement this method yet");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MockSorRole that = (MockSorRole) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sorId != null ? !sorId.equals(that.sorId) : that.sorId != null) return false;
        if (systemOfRecord != null ? !systemOfRecord.equals(that.systemOfRecord) : that.systemOfRecord != null)
            return false;
        if (terminationReason != null ? !terminationReason.equals(that.terminationReason) : that.terminationReason != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sorId != null ? sorId.hashCode() : 0);
        result = 31 * result + (systemOfRecord != null ? systemOfRecord.hashCode() : 0);
        result = 31 * result + (terminationReason != null ? terminationReason.hashCode() : 0);
        return result;
    }

   public void standardizeNormalize(){
   }

}