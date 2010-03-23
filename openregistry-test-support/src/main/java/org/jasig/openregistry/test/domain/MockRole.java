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

    public MockRole(final SorRole sorRole) {
        this.sorRoleId = sorRole.getId();
        this.end = sorRole.getEnd();
        this.terminationReason = sorRole.getTerminationReason();
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

	public Sponsor addSponsor(SorSponsor sorSponsor) {
		return null;
	}

	public Sponsor getSponsor() {
		return null;
	}

	public Type getTerminationReason() {
		return terminationReason;
	}

	public boolean isTerminated() {
		return false;
	}

	public void setSorRoleId(Long sorRoleId) {
		this.sorRoleId = sorRoleId;
	}

	public Long getSorRoleId() {
		return sorRoleId;
	}

	public Set<Address> getAddresses() {
		return null;
	}

	public Address addAddress(Address sorAddress) {
		return null;
	}

	public Set<Phone> getPhones() {
		return null;
	}

	public Set<EmailAddress> getEmailAddresses() {
		return null;
	}

	public EmailAddress addEmailAddress() {
		return null;
	}

	public EmailAddress addEmailAddress(EmailAddress sorEmailAddress) {
		return null;
	}


	public Phone addPhone(Phone sorPhone) {
		return null;
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

	public Date getStart() {
		return null;
	}

	public Date getEnd() {
		return this.end;
	}

    public void setCode(String code) {
        //NOP
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
}