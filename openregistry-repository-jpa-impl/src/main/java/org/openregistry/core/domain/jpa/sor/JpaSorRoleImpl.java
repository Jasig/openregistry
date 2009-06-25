package org.openregistry.core.domain.jpa.sor;

import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.*;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.OrganizationalUnit;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.internal.Entity;
import org.hibernate.envers.Audited;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 11:15:09 AM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorRole")
@Table(name="prs_role_records")
@Audited
@ValidateDefinition
public final class JpaSorRoleImpl extends Entity implements SorRole {

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_role_records_seq")
    @SequenceGenerator(name="prs_role_records_seq",sequenceName="prs_role_records_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    @NotEmpty
    private String sorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorUrlImpl.class)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Url> urls = new ArrayList<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorEmailAddressImpl.class)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorPhoneImpl.class)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Phone> phones = new ArrayList<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorAddressImpl.class)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Address> addresses = new ArrayList<Address>();

    @Column(name="source_sor_id", nullable = false)
    @NotEmpty
    private String sourceSorIdentifier;

    @ManyToOne(optional = false)
    @JoinColumn(name="sor_person_id", nullable=false)
    private JpaSorPersonImpl person;

    @Column(name="percent_time",nullable=false)
    @MinValue(0)
    @MaxValue(100)
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_status_t")
    @NotNull
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch=FetchType.EAGER, targetEntity = JpaSorLeaveImpl.class)
    @ValidateList
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Leave> leaves = new ArrayList<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    @NotNull
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="sponsor_id")
    @NotNull (customCode="sponsorRequiredMsg")
    private JpaSorSponsorImpl sponsor;

    @Column(name="affiliation_date",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull(customCode="startDateRequiredMsg")
    private Date start;

    @Column(name="termination_date")
    @Temporal(TemporalType.DATE)
    private Date end;

    @ManyToOne()
    @JoinColumn(name="termination_t")
    private JpaTypeImpl terminationReason;

    @Column(name="prc_role_id",nullable = true)
    private Long roleId;

    public JpaSorRoleImpl() {
        // nothing to do
    }
    
    public JpaSorRoleImpl(final JpaRoleInfoImpl roleInfo, final JpaSorPersonImpl sorPerson) {
        this.roleInfo = roleInfo;
        this.person = sorPerson;
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

    public void setRoleInfo(RoleInfo roleInfo) {
    	this.roleInfo = (JpaRoleInfoImpl) roleInfo;
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

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return this.roleId;
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
}
