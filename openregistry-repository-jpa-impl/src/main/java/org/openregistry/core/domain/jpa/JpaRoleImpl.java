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
package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.sor.SorSponsor;
import org.hibernate.envers.Audited;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.DateAfter;
import org.javalid.annotations.validation.NotNull;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Role entity mapped to a persistence store with JPA annotations.
 *
 * Unique constraints assume that each person can have only one record of each role
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
// TODO should a person be able to have multiple entries for the same role?  Perhaps if the affiliation date is unique?
@javax.persistence.Entity(name = "role")
@Table(name = "prc_role_records", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id","role_id"}))
@ValidateDefinition
@Audited
public final class JpaRoleImpl extends Entity implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_role_records_seq")
    @SequenceGenerator(name = "prc_role_records_seq", sequenceName = "prc_role_records_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaUrlImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Url> urls = new HashSet<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaEmailAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaPhoneImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Phone> phones = new HashSet<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Address> addresses = new HashSet<Address>();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sponsor_id")
    @NotNull(customCode = "sponsorRequiredMsg")
    private JpaSponsorImpl sponsor;

    @Column(name = "percent_time", nullable = false)
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_status_t")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaLeaveImpl.class)
    private Set<Leave> leaves = new HashSet<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private JpaPersonImpl person;

    @Column(name = "affiliation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(customCode = "startDateRequiredMsg")
    private Date start;

    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne()
    @JoinColumn(name = "termination_t")
    private JpaTypeImpl terminationReason;

    @Column(name="prs_role_id",nullable = true)
    private Long sorRoleId;

    public JpaRoleImpl() {
        // nothing to do
    }

    public JpaRoleImpl(final JpaRoleInfoImpl roleInfo, final JpaPersonImpl person) {
        this.roleInfo = roleInfo;
        this.person = person;
    }

    public Long getId() {
        return this.id;
    }

    public RoleInfo getRoleInfo() {
        return this.roleInfo;
    }

    public void setSorRoleId(final Long sorRoleId) {
        this.sorRoleId = sorRoleId;
    }

    public Long getSorRoleId() {
        return this.sorRoleId;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public Set<Phone> getPhones() {
        return this.phones;
    }

    public Set<EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public Set<Url> getUrls() {
        return this.urls;
    }

    public Address addAddress() {
        final JpaAddressImpl jpaAddress = new JpaAddressImpl(this);
        this.addresses.add(jpaAddress);
        return jpaAddress;
    }

    public Address addAddress(Address sorAddress) {
        final JpaAddressImpl jpaAddress = (JpaAddressImpl) this.addAddress();
        jpaAddress.setLine1(sorAddress.getLine1());
        jpaAddress.setLine2(sorAddress.getLine2());
        jpaAddress.setLine3(sorAddress.getLine3());
        jpaAddress.setCity(sorAddress.getLine1());
        jpaAddress.setRegion(sorAddress.getRegion());
        jpaAddress.setPostalCode(sorAddress.getPostalCode());
        jpaAddress.setCountry(sorAddress.getCountry());
        jpaAddress.setType(sorAddress.getType());
        return jpaAddress;
    }

    public Url addUrl() {
        final JpaUrlImpl url = new JpaUrlImpl(this);
        this.urls.add(url);
        return url;
    }

    public Url addUrl(Url sorUrl) {
        final JpaUrlImpl url = (JpaUrlImpl) this.addUrl();
        url.setUrl(sorUrl.getUrl());
        url.setType(sorUrl.getType());
        return url;
    }

    public EmailAddress addEmailAddress() {
        final JpaEmailAddressImpl jpaEmailAddress = new JpaEmailAddressImpl(this);
        this.emailAddresses.add(jpaEmailAddress);
        return jpaEmailAddress;
    }

    public EmailAddress addEmailAddress(EmailAddress sorEmailAddress) {
        final JpaEmailAddressImpl jpaEmailAddress = (JpaEmailAddressImpl) this.addEmailAddress();
        jpaEmailAddress.setAddress(sorEmailAddress.getAddress());
        jpaEmailAddress.setAddressType(sorEmailAddress.getAddressType());
        return jpaEmailAddress;
    }

    public Phone addPhone() {
        final JpaPhoneImpl jpaPhone = new JpaPhoneImpl(this);
        this.phones.add(jpaPhone);
        return jpaPhone;
    }

    public Phone addPhone(Phone sorPhone) {
        final JpaPhoneImpl jpaPhone = (JpaPhoneImpl) this.addPhone();
        jpaPhone.setCountryCode(sorPhone.getCountryCode());
        jpaPhone.setAreaCode(sorPhone.getAreaCode());
        jpaPhone.setNumber(sorPhone.getNumber());
        jpaPhone.setExtension(sorPhone.getExtension());
        jpaPhone.setPhoneType(sorPhone.getPhoneType());
        jpaPhone.setAddressType(sorPhone.getAddressType());
        return jpaPhone;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
    }

    // TODO calling this setSponsor sort of violates Bean specification
    public Sponsor setSponsor() {
        final JpaSponsorImpl sponsor = new JpaSponsorImpl(this);
        this.sponsor = sponsor;
        return sponsor;
    }

    public Sponsor addSponsor(SorSponsor sorSponsor) {
        final JpaSponsorImpl sponsor = new JpaSponsorImpl(this);
        sponsor.setSponsorId(sorSponsor.getSponsorId());
        sponsor.setType(sorSponsor.getType());
        sponsor.addRole(this);
        this.sponsor = sponsor;
        return sponsor;
    }

    public Sponsor getSponsor() {
        return this.sponsor;
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

    public void moveRoleToPerson(Person person){
        Assert.isInstanceOf(JpaPersonImpl.class, person);
        this.person = (JpaPersonImpl) person;
    }

    public Set<Leave> getLeaves() {
        return this.leaves;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return this.roleInfo.getOrganizationalUnit();
    }

    public Campus getCampus() {
        return this.roleInfo.getCampus();
    }

    public String getLocalCode() {
        return this.roleInfo.getCode();
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

    public boolean isTerminated() {
        return ((this.end != null) && (this.end.compareTo(new Date()) >= 0));
    }

    public void moveToPerson(JpaPersonImpl person){
        this.person = person;
    }

    public String getCode() {
        return this.roleInfo.getCode();
    }

    public String getDisplayableName() {
        return this.roleInfo.getDisplayableName();
    }
}
