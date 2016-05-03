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

import org.hibernate.annotations.Index;
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
@Table(name = "prc_role_records", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id","affiliation_t","organizational_unit_id"}))
@Audited
@org.hibernate.annotations.Table(appliesTo = "prc_role_records", indexes = {
        @Index(name = "PRC_ROLE_RECORDS_PRS_STAT_IDX", columnNames = "PERSON_STATUS_T"),
        @Index(name = "PRC_ROLE_RECORDS_SPONSOR_IDX", columnNames = "SPONSOR_T"),
        @Index(name = "PRC_ROLE_RECORDS_TERM_IDX", columnNames = "TERMINATION_T"),
        @Index(name = "PRC_ROLE_REC_PRS_ROLE_REC_IDX", columnNames = "PRS_ROLE_ID")
})
public class JpaRoleImpl extends Entity implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_role_records_seq")
    @SequenceGenerator(name = "prc_role_records_seq", sequenceName = "prc_role_records_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY, targetEntity = JpaUrlImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Url> urls = new HashSet<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY, targetEntity = JpaEmailAddressImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY, targetEntity = JpaPhoneImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Phone> phones = new HashSet<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY, targetEntity = JpaAddressImpl.class)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY, targetEntity = JpaLeaveImpl.class)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<Leave> leaves = new HashSet<Leave>();

    @Column(name="title",nullable = false, length = 100)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name="organizational_unit_id")
    private JpaOrganizationalUnitImpl organizationalUnit;

    @ManyToOne(optional = false)
    @JoinColumn(name="affiliation_t")
    private JpaTypeImpl affiliationType;

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

    @Column(name="PHI",nullable = true)
    private String PHI;

    @Column(name="RBHS",nullable = true)
    private String RBHS;

    public JpaRoleImpl() {
        // nothing to do
    }

    public JpaRoleImpl(final JpaOrganizationalUnitImpl organizationalUnit, final String title, final JpaTypeImpl affiliationType, final JpaPersonImpl person) {
        this.organizationalUnit = organizationalUnit;
        this.title = title;
        this.affiliationType = affiliationType;
        this.person = person;
    }

    public JpaRoleImpl(final JpaSorRoleImpl sorRole, final JpaPersonImpl person) {
        this((JpaOrganizationalUnitImpl)sorRole.getOrganizationalUnit(), (String)sorRole.getTitle(), (JpaTypeImpl)sorRole.getAffiliationType(), person);
        recalculate(sorRole);
    }

    @Override
    public Long getId() {
        return this.id;
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
        jpaAddress.setLine1(sorAddress.getLine1());
        jpaAddress.setLine2(sorAddress.getLine2());
        jpaAddress.setLine3(sorAddress.getLine3());
        jpaAddress.setCity(sorAddress.getCity());
        jpaAddress.setRegion(sorAddress.getRegion());
        jpaAddress.setPostalCode(sorAddress.getPostalCode());
        jpaAddress.setCountry(sorAddress.getCountry());
        jpaAddress.setBldgNo(sorAddress.getBldgNo());
        jpaAddress.setRoomNo(sorAddress.getRoomNo());

        jpaAddress.setType(sorAddress.getType());
        this.addresses.add(jpaAddress);
        return jpaAddress;
    }

    protected Url addUrl(final Url sorUrl) {
        final JpaUrlImpl url = new JpaUrlImpl(this);
        url.setUrl(sorUrl.getUrl());
        url.setType(sorUrl.getType());
        this.urls.add(url);
        return url;
    }

    protected EmailAddress addEmailAddress(final EmailAddress sorEmailAddress) {
        final JpaEmailAddressImpl jpaEmailAddress = new JpaEmailAddressImpl(this);
        jpaEmailAddress.setAddress(sorEmailAddress.getAddress());
        jpaEmailAddress.setAddressType(sorEmailAddress.getAddressType());
        this.emailAddresses.add(jpaEmailAddress);
        return jpaEmailAddress;
    }

    protected Phone addPhone(final Phone sorPhone) {
        final JpaPhoneImpl jpaPhone = new JpaPhoneImpl(this);
        jpaPhone.setCountryCode(sorPhone.getCountryCode());
        jpaPhone.setAreaCode(sorPhone.getAreaCode());
        jpaPhone.setNumber(sorPhone.getNumber());
        jpaPhone.setExtension(sorPhone.getExtension());
        jpaPhone.setPhoneType(sorPhone.getPhoneType());
        jpaPhone.setAddressType(sorPhone.getAddressType());
        jpaPhone.setPhoneLineOrder(sorPhone.getPhoneLineOrder());
        this.phones.add(jpaPhone);
        return jpaPhone;
    }

    protected Leave addLeave(final Leave sorLeave) {
        final JpaLeaveImpl jpaLeaveImpl = new JpaLeaveImpl(this, sorLeave);
        this.leaves.add(jpaLeaveImpl);
        return jpaLeaveImpl;
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

    public String getDisplayableName() {
        return getTitle() + "/"+ getOrganizationalUnit().getName();
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

    public void expireNow(final Type terminationReason, final boolean orphaned) {
        Assert.isInstanceOf(JpaTypeImpl.class, terminationReason);
//        Assert.isTrue(this.end == null || this.end.compareTo(new Date()) < 0, "this role has already been expired."); expire it again even if it is already expire
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
        this.setOrganizationalUnit( sorRole.getOrganizationalUnit());
        this.title =sorRole.getTitle();

        //The Easier way to recalculate is to clear the collections and just re-insert.  Unfortunately due to a document hibernate bug
        //this implementation was not possible.  Bug causes a unique constraint violation. The inserts are done before the deletes.
        //work around code below.
        //this.addresses.clear();
        //this.phones.clear();
        //this.emailAddresses.clear();
        //this.urls.clear();

        //currently leaves not fully implemented
        this.leaves.clear();
        for (final Leave leave : sorRole.getLeaves()) {
            addLeave(leave);
        }

        //put in code to work around Hibernate bug.
        recalculateAddresses(sorRole);
        recalculateEmailAddresses(sorRole);
        recalculatePhones(sorRole);
        recalculateUrls(sorRole);

        this.setSponsorId(sorRole.getSponsorId());
        this.setSponsorType(sorRole.getSponsorType());

        //set PHI and RBHS flags
        this.setPHI(sorRole.getPHI());
        this.setRBHS(sorRole.getRBHS());
    }

    protected void recalculateAddresses(SorRole sorRole){
        //remove addresses from calculated role when they are not in sorRole.
        //unique constraint guarantees that there can be only one address of a given type per role.
        Set<Address> addressesToDelete = new HashSet<Address>();
        for (final Address cAddress: this.addresses) {
            boolean foundAddress = false;
            for (final Address sorAddress : sorRole.getAddresses()) {
                if (sorAddress.getType().getDescription().equals(cAddress.getType().getDescription())) foundAddress = true;
            }
            if (!foundAddress) addressesToDelete.add(cAddress);
        }

        for (final Address address: addressesToDelete) this.addresses.remove(address);

        //add or update roles that are in sorRole in the calculated role.
        for (final Address sorAddress: sorRole.getAddresses()) {
            Address address = findAddress(sorAddress.getType());
        	if (address == null){
                addAddress(sorAddress);
            } else {
                address.setLine1(sorAddress.getLine1());
                address.setLine2(sorAddress.getLine2());
                address.setLine3(sorAddress.getLine3());
                address.setCity(sorAddress.getCity());
                address.setRegion(sorAddress.getRegion());
                address.setPostalCode(sorAddress.getPostalCode());
                address.setCountry(sorAddress.getCountry());
                address.setBldgNo(sorAddress.getBldgNo());
                address.setRoomNo(sorAddress.getRoomNo());
            }
        }
    }

    protected void recalculateEmailAddresses(SorRole sorRole){
         //remove email addresses from calculated role when they are not in sorRole.
        //unique constraint guarantees that there can be only one emailaddress of a given type per role.
        Set<EmailAddress> emailsToDelete = new HashSet<EmailAddress>();
        for (final EmailAddress cEmailAddress: this.emailAddresses) {
            boolean foundAddress = false;
            for (final EmailAddress sorEmailAddress : sorRole.getEmailAddresses()) {
                if (sorEmailAddress.getAddressType().getDescription().equals(cEmailAddress.getAddressType().getDescription())) foundAddress = true;
            }
            if (!foundAddress) emailsToDelete.add(cEmailAddress);
        }

        for (final EmailAddress email: emailsToDelete) this.emailAddresses.remove(email);

        for (final EmailAddress sorEmailAddress: sorRole.getEmailAddresses()) {
            EmailAddress emailAddress = findEmailAddress(sorEmailAddress.getAddressType());
        	if (emailAddress == null){
        	    addEmailAddress(sorEmailAddress);
            } else {
                emailAddress.setAddress(sorEmailAddress.getAddress());
            }
        }
    }

    protected void recalculatePhones(SorRole sorRole){
        //remove phones from calculated role when they are not in sorRole.
        //unique constraint guarantees that there can be only one phone of a given type per role.
        Set<Phone> phonesToDelete = new HashSet<Phone>();
        for (final Phone cPhone: this.phones) {
            boolean found = false;
            for (final Phone sorPhone : sorRole.getPhones()) {
                if (sorPhone.getAddressType().getDescription().equals(cPhone.getAddressType().getDescription()) &&
                    sorPhone.getPhoneType().getDescription().equals(cPhone.getPhoneType().getDescription()) &&
                    sorPhone.getPhoneLineOrder().compareTo(cPhone.getPhoneLineOrder()) == 0) found = true;
            }
            if (!found) phonesToDelete.add(cPhone);
        }

        for (final Phone phone: phonesToDelete) this.phones.remove(phone);

        for (final Phone sorPhone: sorRole.getPhones()) {
            Phone phone = findPhone(sorPhone.getAddressType(), sorPhone.getPhoneType(), sorPhone.getPhoneLineOrder());
            if (phone == null){
        	    addPhone(sorPhone);
            } else {
                phone.setCountryCode(sorPhone.getCountryCode());
                phone.setAreaCode(sorPhone.getAreaCode());
                phone.setNumber(sorPhone.getNumber());
                phone.setExtension(sorPhone.getExtension());
            }
        }
    }

    protected void recalculateUrls(SorRole sorRole){
         //remove urls from calculated role when they are not in sorRole.
        Set<Url> urlsToDelete = new HashSet<Url>();
        for (final Url cUrl: this.urls) {
            boolean found = false;
            for (final Url sorUrl : sorRole.getUrls()) {
                if (sorUrl.getUrl().equals(cUrl)) found = true;
            }
            if (!found) urlsToDelete.add(cUrl);
        }

        for (final Url url: urlsToDelete) this.urls.remove(url);

        for (final Url sorUrl: sorRole.getUrls()) {
            Url url = findUrl(sorUrl);
        	if (url == null){
        	    addUrl(sorUrl);
            } else {
                url.setUrl(sorUrl.getUrl());
            }
        }
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

    protected Phone findPhone(Type addressType, Type phoneType, Integer phoneLineOrder){
        for (final Phone phone : this.phones) {
            if (phone.getAddressType().getDescription().equals(addressType.getDescription()) &&
                phone.getPhoneType().getDescription().equals(phoneType.getDescription()) &&
                phone.getPhoneLineOrder().compareTo(phoneLineOrder) == 0) return phone;
        }
        return null;
    }

    protected Url findUrl(Url sorUrl){
        for (final Url url : this.urls) {
            if (sorUrl.equals(url)) return url;
        }
        return null;
    }

    @Override
    public int compareTo(final Role o) {
        // TODO implement
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
