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
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
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

	private String sourceSorIdentifier;

	private Type terminationReason;

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

    public String getSourceSorIdentifier() {
		return sourceSorIdentifier;
	}

	public void setSourceSorIdentifier(String sorIdentifier) {
		this.sourceSorIdentifier = sorIdentifier;
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

	public RoleInfo getRoleInfo() {
		return null;
	}

	public SorSponsor setSponsor() {
		return null;
	}

	public SorSponsor getSponsor() {
		return null;
	}

	public Type getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(Type reason) {
		this.terminationReason = reason;
	}

	public List<Address> getAddresses() {
		return null;
	}

	public Address addAddress() {
		return null;
	}

	public Address removeAddressById(Long id) {
		return null;
	}

	public Address getAddress(long id) {
		return null;
	}

	public List<Phone> getPhones() {
		return new ArrayList<Phone>();
	}

	public List<EmailAddress> getEmailAddresses() {
		return new ArrayList<EmailAddress>();
	}

	public EmailAddress removeEmailAddressById(Long id) {
		return null;
	}

	public EmailAddress addEmailAddress() {
		return null;
	}

	public Phone addPhone() {
		return null;
	}

	public Phone removePhoneById(Long id) {
		return null;
	}

	public List<Url> getUrls() {
		return null;
	}

	public Url addUrl() {
		return null;
	}

	public Url removeURLById(Long id) {
		return null;
	}

	public List<Leave> getLeaves() {
		return null;
	}

	public String getTitle() {
		return null;
	}

	public OrganizationalUnit getOrganizationalUnit() {
		return null;
	}

	public Campus getCampus() {
		return null;
	}

	public Type getAffiliationType() {
		return null;
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
	}

	public Date getStart() {
		return null;
	}

	public Date getEnd() {
		return null;
	}

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MockSorRole that = (MockSorRole) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sorId != null ? !sorId.equals(that.sorId) : that.sorId != null) return false;
        if (sourceSorIdentifier != null ? !sourceSorIdentifier.equals(that.sourceSorIdentifier) : that.sourceSorIdentifier != null)
            return false;
        if (terminationReason != null ? !terminationReason.equals(that.terminationReason) : that.terminationReason != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sorId != null ? sorId.hashCode() : 0);
        result = 31 * result + (sourceSorIdentifier != null ? sourceSorIdentifier.hashCode() : 0);
        result = 31 * result + (terminationReason != null ? terminationReason.hashCode() : 0);
        return result;
    }

   public void standardizeNormalize(){
   }

    @Override
    public SystemOfRecord getSystemOfRecord() {
        return null;
    }
}