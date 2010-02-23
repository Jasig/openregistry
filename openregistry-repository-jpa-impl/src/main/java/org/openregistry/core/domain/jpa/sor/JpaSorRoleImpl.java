/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.domain.jpa.sor;

import org.hibernate.annotations.*;
import org.hibernate.envers.*;
import org.openregistry.core.domain.*;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.annotation.AllowedTypes;
import org.openregistry.core.domain.annotation.RequiredSize;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.*;
import org.openregistry.core.domain.sor.*;
import org.springframework.util.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;

/**
 * JPA-backed implementation of the System of Record role.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
@javax.persistence.Entity(name="sorRole")
@Table(name="prs_role_records", uniqueConstraints = @UniqueConstraint(columnNames = {"source_sor_id","id"}))
@Audited
public class JpaSorRoleImpl extends Entity implements SorRole {

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_role_records_seq")
    @SequenceGenerator(name="prs_role_records_seq",sequenceName="prs_role_records_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    @NotNull
    @Size(min=1)
    private String sorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorUrlImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.urls")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Url> urls = new ArrayList<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorEmailAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.emailAddresses")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorPhoneImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.phones")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Phone> phones = new ArrayList<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.addresses")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Address> addresses = new ArrayList<Address>();

    @Column(name="source_sor_id", nullable = false)
    @NotNull
    @Size(min=1)
    private String sourceSorIdentifier;

	@ManyToOne(optional = false)
    @JoinColumn(name="sor_person_id", nullable=false)
    private JpaSorPersonImpl person;

    @Column(name="percent_time",nullable=false)
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_status_t")
    @NotNull
    @AllowedTypes(property = "role.personStatus")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch=FetchType.EAGER, targetEntity = JpaSorLeaveImpl.class)
    @RequiredSize(property = "role.leaves")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Leave> leaves = new ArrayList<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    @NotNull
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="sponsor_id")
    @NotNull(message="sponsorRequiredMsg")
    private JpaSorSponsorImpl sponsor;

    @Column(name="affiliation_date",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull(message="startDateRequiredMsg")
    private Date start;

    @Column(name="termination_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne()
    @JoinColumn(name="termination_t")
    private JpaTypeImpl terminationReason;

    public JpaSorRoleImpl() {
        // nothing to do
    }

    public JpaSorRoleImpl(final JpaRoleInfoImpl roleInfo, final JpaSorPersonImpl sorPerson) {
        this.roleInfo = roleInfo;
        this.person = sorPerson;
    }

    public String getCode() {
        return this.roleInfo.getCode();
    }

    public void expireNow(final Type terminationReason) {
        expire(terminationReason, new Date());
    }

    public void expire(final Type terminationReason, final Date expirationDate) {
        Assert.isInstanceOf(JpaTypeImpl.class, terminationReason);
        this.end = expirationDate;
        this.terminationReason = (JpaTypeImpl) terminationReason;
    }

    public String getDisplayableName() {
        return this.roleInfo.getDisplayableName();
    }

    public Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public void setSorId(String sorId){
        this.sorId = sorId;
    }

    public String getSourceSorIdentifier() {
        return this.sourceSorIdentifier;
    }

    public void setSourceSorIdentifier(final String sorIdentifier) {
        this.sourceSorIdentifier = sorIdentifier;
    }

    public RoleInfo getRoleInfo() {
    	return this.roleInfo;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setPercentage(final int percentage) {
        this.percentage = percentage;
    }

    public Type getPersonStatus() {
        return this.personStatus;
    }

    public void setPersonStatus(final Type personStatus) {
        Assert.isInstanceOf(JpaTypeImpl.class, personStatus);
        this.personStatus = (JpaTypeImpl) personStatus;
    }

    // TODO this is not setSponsor.  Setters should be for JavaBean properties
    public SorSponsor setSponsor() {
       	final JpaSorSponsorImpl sponsor = new JpaSorSponsorImpl(this);
        this.sponsor = sponsor;
        return sponsor;
    }

    public SorSponsor getSponsor() {
        return this.sponsor;
    }
    public Type getTerminationReason() {
        return this.terminationReason;
    }

    public void setTerminationReason(final Type reason) {
        Assert.isInstanceOf(JpaTypeImpl.class, reason);
        this.terminationReason = (JpaTypeImpl) reason;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setStart(final Date date) {
        this.start = date;
    }

    public void setEnd(final Date date) {
        this.end = date;
    }

	public Address addAddress() {
	    final JpaSorAddressImpl jpaAddress = new JpaSorAddressImpl(this);
	    this.addresses.add(jpaAddress);
	    return jpaAddress;
	}

    public synchronized Address removeAddressById(final Long id) {
        Address addressToDelete = null;
        for (final Address address : this.addresses) {
            final Long addressId = address.getId();
            if (addressId != null && addressId.equals(id)) {
                addressToDelete = address;
                break;
            }
        }

        if (addressToDelete != null) {
            this.addresses.remove(addressToDelete);
            return addressToDelete;
        }

        return null;
    }

    public Address getAddress(long id){
        for(Address a : this.addresses) {
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }

	public EmailAddress addEmailAddress() {
	    final JpaSorEmailAddressImpl jpaEmailAddress = new JpaSorEmailAddressImpl(this);
	    this.emailAddresses.add(jpaEmailAddress);
	    return jpaEmailAddress;
	}

    public synchronized EmailAddress removeEmailAddressById(final Long id) {
        EmailAddress emailAddressToDelete = null;
        for (final EmailAddress emailAddress : this.emailAddresses) {
            final Long emailAddressId = emailAddress.getId();
            if (emailAddressId != null && emailAddressId.equals(id)) {
                emailAddressToDelete = emailAddress;
                break;
            }
        }

        if (emailAddressToDelete != null) {
            this.emailAddresses.remove(emailAddressToDelete);
            return emailAddressToDelete;
        }

        return null;
    }

	public Phone addPhone() {
	    final JpaSorPhoneImpl jpaPhone = new JpaSorPhoneImpl(this);
	    this.phones.add(jpaPhone);
	    return jpaPhone;
	}

    public synchronized Phone removePhoneById(final Long id) {
        Phone phoneToDelete = null;
        for (final Phone phone : this.phones) {
            final Long phoneId = phone.getId();
            if (phoneId != null && phoneId.equals(id)) {
                phoneToDelete = phone;
                break;
            }
        }

        if (phoneToDelete != null) {
            this.phones.remove(phoneToDelete);
            return phoneToDelete;
        }

        return null;
    }

	public Url addUrl() {
	    final JpaSorUrlImpl jpaUrl = new JpaSorUrlImpl(this);
	    this.urls.add(jpaUrl);
	    return jpaUrl;
	}

    public synchronized Url removeURLById(final Long id) {
        Url urlToDelete = null;
        for (final Url url : this.urls) {
            final Long urlId = url.getId();
            if (urlId != null && urlId.equals(id)) {
                urlToDelete = url;
                break;
            }
        }

        if (urlToDelete != null) {
            this.urls.remove(urlToDelete);
            return urlToDelete;
        }

        return null;
    }

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public List<Leave> getLeaves() {
		return this.leaves;
	}

	public List<Phone> getPhones() {
		return this.phones;
	}

	public List<Url> getUrls() {
		return this.urls;
	}

	public Campus getCampus() {
		return this.roleInfo.getCampus();
	}

	public String getLocalCode() {
        return this.roleInfo.getCode();
	}

	public OrganizationalUnit getOrganizationalUnit() {
		return this.roleInfo.getOrganizationalUnit();
	}

    public List<EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public void moveToPerson(JpaSorPersonImpl person){
        this.person = person;
    }

    public void standardizeNormalize(){
      //TBD
    }
}
