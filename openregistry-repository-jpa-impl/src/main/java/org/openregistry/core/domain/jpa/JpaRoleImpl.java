package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.*;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Role entity mapped to a persistence store with JPA annotations.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@javax.persistence.Entity(name="role")
@Table(name="prc_sor_persons")
@SecondaryTables({@SecondaryTable(name = "prs_sor_role_records",pkJoinColumns = @PrimaryKeyJoinColumn(name="sor_person_id")), @SecondaryTable(name = "prs_roles", pkJoinColumns = @PrimaryKeyJoinColumn(name="role_id"))})
public class JpaRoleImpl extends Entity implements Role {

    @Id
    @Column(name="sor_role_record_id",table="prs_sor_role_records")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_sor_role_record_seq")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private List<JpaUrlImpl> urls = new ArrayList<JpaUrlImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private List<JpaEmailAddressImpl> emailAddresses = new ArrayList<JpaEmailAddressImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private List<JpaPhoneImpl> phones = new ArrayList<JpaPhoneImpl>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private List<JpaAddressImpl> addresses = new ArrayList<JpaAddressImpl>();

    @Column(name="title",table="prs_roles",nullable = false, updatable = false, insertable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_id",table="prs_sor_role_records")
    private JpaPersonImpl sponsor;

    @Column(name="percent_time", table="prs_sor_role_records",nullable=false)
    private int percentage;

    @Column(name="code", table="prs_roles",nullable = true, updatable = false, insertable = false)
    private String localCode;

    @ManyToOne(optional=false)
    @JoinColumn(name="campus_id", table="prs_roles")
    private JpaCampusImpl campus;

    @ManyToOne(optional=false)
    @JoinColumn(name="department_id",table="prs_roles")
    private JpaDepartmentImpl department;

    // TODO map type
    private JpaTypeImpl affiliationType;

    // TODO map type
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="role")
    private List<JpaLeaveImpl> leaves = new ArrayList<JpaLeaveImpl>();

    @Embedded
    private JpaActiveImpl active = new JpaActiveImpl();

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false, table="prc_sor_persons")
    private JpaPersonImpl person;

    protected Long getId() {
        return this.id;
    }

    public List<? extends Address> getAddresses() {
        return this.addresses;
    }

    public List<? extends Phone> getPhones() {
        return this.phones;
    }

    public List<? extends EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public List<? extends Url> getUrls() {
        return this.urls;
    }

    public Address addAddress() {
        final JpaAddressImpl jpaAddress = new JpaAddressImpl();
        this.addresses.add(jpaAddress);
        return jpaAddress;
    }

    public Url addUrl() {
        final JpaUrlImpl url = new JpaUrlImpl();
        this.urls.add(url);
        return url;
    }

    public EmailAddress addEmailAddress() {
        final JpaEmailAddressImpl jpaEmailAddress = new JpaEmailAddressImpl();
        this.emailAddresses.add(jpaEmailAddress);
        return jpaEmailAddress;
    }

    public Phone addPhone() {
        final JpaPhoneImpl jpaPhone = new JpaPhoneImpl();
        this.phones.add(jpaPhone);
        return jpaPhone;
    }

    public String getTitle() {
        return this.title;
    }

    public Type getAffiliationType() {
        return this.affiliationType;
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

    public List<? extends Leave> getLeaves() {
        return this.leaves;
    }

    public Active getActive() {
        return this.active;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Campus getCampus() {
        return this.campus;
    }

    public String getLocalCode() {
        return this.localCode;
    }

    public Type getAfilliationType() {
        return this.affiliationType;
    }
}
