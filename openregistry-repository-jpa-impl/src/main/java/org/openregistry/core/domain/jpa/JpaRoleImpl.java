package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.*;

import javax.persistence.*;
import java.util.*;

import org.javalid.annotations.core.JvGroup;
import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.DateAfter;
import org.hibernate.envers.Audited;

/**
 * Role entity mapped to a persistence store with JPA annotations.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="role")
@Table(name="prc_role_records")
@ValidateDefinition
@Audited
public class JpaRoleImpl extends Entity implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prc_role_records_seq")
    @SequenceGenerator(name="prc_role_records_seq",sequenceName="prc_role_records_seq",initialValue=1,allocationSize=50)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role",fetch = FetchType.EAGER, targetEntity = JpaUrlImpl.class)      
    private Set<Url> urls = new HashSet<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role",fetch = FetchType.EAGER, targetEntity = JpaEmailAddressImpl.class)
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role",fetch = FetchType.EAGER, targetEntity = JpaPhoneImpl.class)
    private Set<Phone> phones = new HashSet<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role",fetch = FetchType.EAGER, targetEntity = JpaAddressImpl.class)
    private Set<Address> addresses = new HashSet<Address>();

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_id")
    @NotNull (customCode="sponsorRequiredMsg")
    private JpaPersonImpl sponsor;

    @Column(name="percent_time",nullable=false)
    private int percentage;

    @Column(name="code", nullable = true, updatable = false, insertable = false)
    private String localCode;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_status_t")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role",fetch=FetchType.EAGER, targetEntity = JpaLeaveImpl.class)
    private Set<Leave> leaves = new HashSet<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_id", nullable=false)
    private JpaPersonImpl person;

    @Column(name="affiliation_date",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull (customCode="startDateRequiredMsg")
    private Date start;

    @Column(name="termination_date")
    @Temporal(TemporalType.DATE)
    @DateAfter(after = "#{parent.start}", customCode = "startDateEndDateCompareMsg")
    private Date end;

    @ManyToOne()
    @JoinColumn(name="termination_t")
    private JpaTypeImpl terminationReason;

    public JpaRoleImpl() {
        // nothing to do
    }

    public JpaRoleImpl(JpaRoleInfoImpl roleInfo, JpaPersonImpl person) {
        this.roleInfo = roleInfo;
        this.person = person;
    }

    protected Long getId() {
        return this.id;
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

    public Url addUrl() {
        final JpaUrlImpl url = new JpaUrlImpl(this);
        this.urls.add(url);
        return url;
    }

    public EmailAddress addEmailAddress() {
        final JpaEmailAddressImpl jpaEmailAddress = new JpaEmailAddressImpl(this);
        this.emailAddresses.add(jpaEmailAddress);
        return jpaEmailAddress;
    }

    public Phone addPhone() {
        final JpaPhoneImpl jpaPhone = new JpaPhoneImpl(this);
        this.phones.add(jpaPhone);
        return jpaPhone;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
    }

    public void setSponsor(final Person sponsor) {
        if (!(sponsor instanceof JpaPersonImpl)) {
            throw new IllegalArgumentException("sponsor must be of type JpaPersonImpl.");
        }
        this.sponsor = (JpaPersonImpl) sponsor;
    }

    public Person getSponsor() {
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
        if (!(personStatus instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.personStatus = (JpaTypeImpl) personStatus;
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
        return this.localCode;
    }

    public Type getTerminationReason() {
        return this.terminationReason;
    }

    public void setTerminationReason(final Type reason) {
        if (!(reason instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }
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
}
