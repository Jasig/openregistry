package org.openregistry.core.domain;

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

	public MockRole(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public Sponsor setSponsor() {
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

	public void setTerminationReason(Type reason) {
		this.terminationReason = reason;
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

	public Address addAddress() {
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

	public Phone addPhone() {
		return null;
	}

	public Phone addPhone(Phone sorPhone) {
		return null;
	}

	public Phone removePhoneById(Long id) {
		return null;
	}

	public Set<Url> getUrls() {
		return null;
	}

	public Url addUrl() {
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

	public void setStart(Date date) {
	}

	public void setEnd(final Date date) {
        this.end = date;
	}

	public Date getStart() {
		return null;
	}

	public Date getEnd() {
		return this.end;
	}

    public void expireNow(final Type terminationReason) {
        expire(terminationReason, new Date());
    }

    public void expire(final Type terminationReason, final Date expirationDate) {
        this.terminationReason = terminationReason;
        this.end = expirationDate;
    }
}