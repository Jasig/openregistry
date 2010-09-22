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

import org.openregistry.core.domain.*;
import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.openregistry.core.domain.sor.SorRole;
import org.hibernate.envers.Audited;
import org.openregistry.core.domain.sor.SystemOfRecord;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;

import org.slf4j.*;

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
@Audited
public class JpaRoleImpl extends Entity implements Role {

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

    @Column(name="sponsor_id", nullable = false)
    private Long sponsorId;

    @ManyToOne(optional = false, targetEntity = JpaTypeImpl.class)
    @JoinColumn(name="sponsor_t")
    private Type sponsorType;

    @Column(name = "percent_time", nullable = false)
    private int percentage;

    @ManyToOne(optional = false, targetEntity = JpaTypeImpl.class)
    @JoinColumn(name = "person_status_t")
    private Type personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER, targetEntity = JpaLeaveImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Leave> leaves = new HashSet<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private JpaPersonImpl person;

    @Column(name = "affiliation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne(targetEntity = JpaTypeImpl.class)
    @JoinColumn(name = "termination_t")
    private Type terminationReason;

    @Column(name="prs_role_id",nullable = true)
    private Long sorRoleId;

    public JpaRoleImpl() {
        // nothing to do
    }

    public JpaRoleImpl(final JpaRoleInfoImpl roleInfo, final JpaPersonImpl person) {
        this.roleInfo = roleInfo;
        this.person = person;
    }

    public JpaRoleImpl(final JpaSorRoleImpl sorRole, final JpaPersonImpl person) {
        this((JpaRoleInfoImpl) sorRole.getRoleInfo(), person);
        recalculate(sorRole);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public RoleInfo getRoleInfo() {
        return this.roleInfo;
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

    protected Address addAddress(final Address sorAddress) {
        final JpaAddressImpl jpaAddress = new JpaAddressImpl(this);
        this.addresses.add(jpaAddress);
        jpaAddress.setLine1(sorAddress.getLine1());
        jpaAddress.setLine2(sorAddress.getLine2());
        jpaAddress.setLine3(sorAddress.getLine3());
        jpaAddress.setCity(sorAddress.getCity());
        jpaAddress.setRegion(sorAddress.getRegion());
        jpaAddress.setPostalCode(sorAddress.getPostalCode());
        jpaAddress.setCountry(sorAddress.getCountry());
        jpaAddress.setType(sorAddress.getType());
        return jpaAddress;
    }

    protected Url addUrl(final Url sorUrl) {
        final JpaUrlImpl url = new JpaUrlImpl(this);
        this.urls.add(url);
        url.setUrl(sorUrl.getUrl());
        url.setType(sorUrl.getType());
        return url;
    }

    protected EmailAddress addEmailAddress(final EmailAddress sorEmailAddress) {
        final JpaEmailAddressImpl jpaEmailAddress = new JpaEmailAddressImpl(this);
        this.emailAddresses.add(jpaEmailAddress);
        jpaEmailAddress.setAddress(sorEmailAddress.getAddress());
        jpaEmailAddress.setAddressType(sorEmailAddress.getAddressType());
        return jpaEmailAddress;
    }

    protected Phone addPhone(final Phone sorPhone) {
        final JpaPhoneImpl jpaPhone = new JpaPhoneImpl(this);
        this.phones.add(jpaPhone);
        jpaPhone.setCountryCode(sorPhone.getCountryCode());
        jpaPhone.setAreaCode(sorPhone.getAreaCode());
        jpaPhone.setNumber(sorPhone.getNumber());
        jpaPhone.setExtension(sorPhone.getExtension());
        jpaPhone.setPhoneType(sorPhone.getPhoneType());
        jpaPhone.setAddressType(sorPhone.getAddressType());
        return jpaPhone;
    }

    protected Leave addLeave(final Leave sorLeave) {
        final JpaLeaveImpl jpaLeaveImpl = new JpaLeaveImpl(this, sorLeave);
        this.leaves.add(jpaLeaveImpl);
        return jpaLeaveImpl;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
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
	    this.sponsorType = type;
	}
    public int getPercentage() {
        return this.percentage;
    }

    public Type getPersonStatus() {
        return this.personStatus;
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

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public boolean isTerminated() {
        return ((this.end != null) && (this.end.compareTo(new Date()) >= 0));
    }

    @Override
    public boolean isNotYetActive() {
        return this.start.compareTo(new Date()) > 0;
    }

    @Override
    public boolean isActive() {
        return !isNotYetActive() && !isTerminated();
    }

    public String getCode() {
        return this.roleInfo.getCode();
    }

    public String getDisplayableName() {
        return this.roleInfo.getDisplayableName();
    }

    public void expireNow(final Type terminationReason, final boolean orphaned) {
        Assert.isInstanceOf(JpaTypeImpl.class, terminationReason);
        Assert.isTrue(this.end == null || this.end.compareTo(new Date()) < 0, "this role has already been expired.");
        this.end = new Date();
        this.terminationReason = terminationReason;
        if (orphaned) {
            this.sorRoleId = null;
        }
    }

    public void recalculate(final SorRole sorRole) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Role recalculate");
        this.personStatus = sorRole.getPersonStatus();
        this.start = sorRole.getStart();
        this.end = sorRole.getEnd();
        this.sorRoleId = sorRole.getId();
        this.percentage =sorRole.getPercentage();
        this.terminationReason = sorRole.getTerminationReason();

        //this.addresses.clear();
        //this.phones.clear();
        //this.emailAddresses.clear();
        this.urls.clear();
        this.leaves.clear();

        for (final Leave leave : sorRole.getLeaves()) {
            addLeave(leave);
        }

	  //search for address first
        for (final Address sorAddress: sorRole.getAddresses()) {
            Address address = findAddress(sorAddress.getType());
        	if (address == null){
                addAddress(sorAddress);
            } else {
                logger.info("Recalculate: Found address");
                address.setLine1(sorAddress.getLine1());
                address.setLine2(sorAddress.getLine2());
                address.setLine3(sorAddress.getLine3());
                address.setCity(sorAddress.getCity());
                address.setRegion(sorAddress.getRegion());
                address.setPostalCode(sorAddress.getPostalCode());
                address.setCountry(sorAddress.getCountry());
            }
        }
        for (final EmailAddress sorEmailAddress: sorRole.getEmailAddresses()) {
            EmailAddress emailAddress = findEmailAddress(sorEmailAddress.getAddressType());
        	if (emailAddress == null){
        	    addEmailAddress(sorEmailAddress);
            } else {
                emailAddress.setAddress(sorEmailAddress.getAddress());
            }
        }
        for (final Phone sorPhone: sorRole.getPhones()) {
            Phone phone = findPhone(sorPhone.getAddressType(), sorPhone.getPhoneType());
            if (phone == null){
        	    addPhone(sorPhone);
            } else {
                phone.setCountryCode(sorPhone.getCountryCode());
                phone.setAreaCode(sorPhone.getAreaCode());
                phone.setNumber(sorPhone.getNumber());
                phone.setExtension(sorPhone.getExtension());
            }
        }

        for (final Url url: sorRole.getUrls()) {
        	addUrl(url);
        }

        this.setSponsorId(sorRole.getSponsorId());
        this.setSponsorType(sorRole.getSponsorType());
    }

    protected Address findAddress(Type type){
        for (final Address address : this.addresses) {
            if (address.getType().getDescription().equals(type.getDescription())) return address;
        }
        return null;
    }

    protected EmailAddress findEmailAddress(Type type){
        for (final EmailAddress emailAddress : this.emailAddresses) {
            if (emailAddress.getAddressType().getDescription().equals(type.getDescription())) return emailAddress;
        }
        return null;
    }

    protected Phone findPhone(Type addressType, Type phoneType){
        for (final Phone phone : this.phones) {
            if (phone.getAddressType().getDescription().equals(addressType.getDescription()) &&
                phone.getPhoneType().getDescription().equals(phoneType.getDescription())) return phone;
        }
        return null;
    }

    @Override
    public SystemOfRecord getSystemOfRecord() {
        return this.roleInfo.getSystemOfRecord();
    }

    @Override
    public int compareTo(final Role o) {
        // TODO implement
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
