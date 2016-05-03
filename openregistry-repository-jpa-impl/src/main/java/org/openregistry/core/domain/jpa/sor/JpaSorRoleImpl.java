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
@javax.persistence.Entity(name = "sorRole")
@Table(name = "prs_role_records", uniqueConstraints = @UniqueConstraint(columnNames = {"system_of_record_id", "id", "affiliation_t"}))
@org.hibernate.annotations.Table(appliesTo = "prs_role_records", indexes = {
    @Index(name = "PRS_ROLE_SOR_PERSON_INDEX", columnNames = "sor_person_id"),

    @Index(name = "PRS_ROLE_REC_ORG_UNIT_ID_IDX", columnNames = "ORGANIZATIONAL_UNIT_ID"),
    @Index(name = "PRS_ROLE_REC_PERS_STAT_T_IDX", columnNames = "PERSON_STATUS_T"),
    @Index(name = "PRS_ROLE_RECORDS_SPONSOR_T_IDX", columnNames = "SPONSOR_T"),
    @Index(name = "PRS_ROLE_REC_TERM_T_IDX", columnNames = "TERMINATION_T")
})
@Audited
public class JpaSorRoleImpl extends Entity implements SorRole {

    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_role_records_seq")
    @SequenceGenerator(name = "prs_role_records_seq", sequenceName = "prs_role_records_seq", initialValue = 1, allocationSize = 50)
    private Long recordId;

    @Column(name = "id")
    @NotNull
    @Size(min = 1)
    private String sorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorRole", fetch = FetchType.LAZY, targetEntity = JpaSorUrlImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.urls")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Url> urls = new ArrayList<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorRole", fetch = FetchType.LAZY, targetEntity = JpaSorEmailAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.emailAddresses")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorRole", fetch = FetchType.LAZY, targetEntity = JpaSorPhoneImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.phones")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Phone> phones = new ArrayList<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorRole", fetch = FetchType.LAZY, targetEntity = JpaSorAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @RequiredSize(property = "role.addresses")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Address> addresses = new ArrayList<Address>();

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "system_of_record_id")
    private JpaSystemOfRecordImpl systemOfRecord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sor_person_id", nullable = false)
    private JpaSorPersonImpl person;

    @Column(name = "percent_time", nullable = false)
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_status_t")
    @NotNull
    @AllowedTypes(property = "role.personStatus")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorRole", fetch = FetchType.LAZY, targetEntity = JpaSorLeaveImpl.class)
    @RequiredSize(property = "role.leaves")
    @Valid
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Leave> leaves = new ArrayList<Leave>();

    @Column(name="sponsor_id")
    @NotNull
    private Long sponsorId;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_t")
    @NotNull
    private JpaTypeImpl sponsorType;

    @Column(name = "affiliation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "{startDateRequiredMsg}")
    private Date start;

    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne()
    @JoinColumn(name = "termination_t")
    private JpaTypeImpl terminationReason;

    @Column(name="title",nullable = false, length = 100)
    @NotNull (message = "{titleRequiredMsg}")

    private String title;

    @Column(name="PHI", nullable = true, length = 1)
    private String PHI;

    @Column(name="RBHS", nullable = true, length = 1)
    private String RBHS;

    @ManyToOne(optional = false)
    @JoinColumn(name="organizational_unit_id")
    @NotNull(message = "{organizationalUnitRequiredMsg}")
    private JpaOrganizationalUnitImpl organizationalUnit;

    @ManyToOne(optional = false)
    @JoinColumn(name="affiliation_t")
    private JpaTypeImpl affiliationType;

    public JpaSorRoleImpl() {
        // nothing to do
    }

    public JpaSorRoleImpl(final Type affiliationType, final SorPerson sorPerson) {
        Assert.isInstanceOf(JpaTypeImpl.class, affiliationType);
        Assert.isInstanceOf(JpaSorPersonImpl.class, sorPerson);
        this.affiliationType = (JpaTypeImpl)affiliationType;
        this.person = (JpaSorPersonImpl)sorPerson;
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
        return getTitle() + "/"+ getOrganizationalUnit().getName();
    }

    public Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public void setSorId(String sorId) {
        this.sorId = sorId;
    }

    public JpaSystemOfRecordImpl getSystemOfRecord() {
        return this.systemOfRecord;
    }

    public void setSystemOfRecord(SystemOfRecord systemOfRecord) {
        Assert.isInstanceOf(JpaSystemOfRecordImpl.class, systemOfRecord);
        this.systemOfRecord = (JpaSystemOfRecordImpl)systemOfRecord;
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

    public Long getSponsorId() {
		return this.sponsorId;
	}

	public Type getSponsorType() {
		return this.sponsorType;
	}

	public void setSponsorId(final Long id) {
		this.sponsorId = id;
	}

	public void setSponsorType(final Type type) {
        Assert.isInstanceOf(JpaTypeImpl.class, type);
        this.sponsorType = (JpaTypeImpl)type;
	}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return this.organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        Assert.isInstanceOf(JpaOrganizationalUnitImpl.class, organizationalUnit);
        this.organizationalUnit = (JpaOrganizationalUnitImpl)organizationalUnit;
    }

    public Type getAffiliationType() {
        return this.affiliationType;
    }

    public void setAffiliationType(Type affiliationType) {
        Assert.isInstanceOf(JpaTypeImpl.class, affiliationType);
        this.affiliationType = (JpaTypeImpl)affiliationType;
    }

    public Address createAddress(){
        final JpaSorAddressImpl jpaAddress = new JpaSorAddressImpl(this);
        return jpaAddress;
    }

    public void addAddress(Address address) {
        Assert.isInstanceOf(JpaSorAddressImpl.class, address);
        this.addresses.add(address);
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

    public Address getAddress(long id) {
        for (Address a : this.addresses) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public EmailAddress createEmailAddress() {
        final JpaSorEmailAddressImpl jpaEmailAddress = new JpaSorEmailAddressImpl(this);
        return jpaEmailAddress;
    }

    public void addEmailAddress(EmailAddress emailAddress) {
        Assert.isInstanceOf(JpaSorEmailAddressImpl.class, emailAddress);
        this.emailAddresses.add(emailAddress);
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

    public Phone createPhone() {
        final JpaSorPhoneImpl jpaPhone = new JpaSorPhoneImpl(this);
        return jpaPhone;
    }

    public void addPhone(Phone phone) {
        Assert.isInstanceOf(JpaSorPhoneImpl.class, phone);
        this.phones.add(phone);
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

    public Url createUrl(){
        final JpaSorUrlImpl jpaUrl = new JpaSorUrlImpl(this);
        return jpaUrl;
    }

    public void addUrl(Url url) {
        Assert.isInstanceOf(JpaSorUrlImpl.class, url);
        this.urls.add(url);
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

    public JpaSorPersonImpl getPerson() {
            return person;
    }

    public List<EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public void moveToPerson(JpaSorPersonImpl person) {
        this.person = person;
    }

    public boolean isTerminated() {
        return ((this.end != null) && (this.end.compareTo(new Date()) <= 0));
    }

    @Override
    public boolean isNotYetActive() {
        return this.start.compareTo(new Date()) > 0;
    }

    @Override
    public boolean isActive() {
        return !isNotYetActive() && !isTerminated();
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
            EmailAddress e = createEmailAddress();
            e.setAddressType(emailType);
            e.setAddress(emailAddress);
            addEmailAddress(e);
        }
    }
}
