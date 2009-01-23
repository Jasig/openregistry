package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.*;

import javax.persistence.*;
import java.util.List;

/**
 * Role entity mapped to a persistence store with JPA annotations.
 *
 * @since 1.0
 *        TODO: add all the properties, dependencies and map to OR DB with JPA annotations
 */
@javax.persistence.Entity
@Table(name="prc_sor_persons")
@SecondaryTables({@SecondaryTable(name = "prs_sor_role_records",pkJoinColumns = @PrimaryKeyJoinColumn(name="sor_person_id")), @SecondaryTable(name = "prs_roles", pkJoinColumns = @PrimaryKeyJoinColumn(name="role_id"))})
public class JpaRoleImpl extends Entity implements Role {

    @OneToMany(cascade = CascadeType.ALL, mappedBy="person")
    private List<JpaUrlImpl> urls;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="person")
    private List<JpaEmailAddressImpl> emailAddresses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="person")
    private List<JpaPhoneImpl> phones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="person")
    private List<JpaAddressImpl> addresses;

    @Column(name="title",table="prs_roles",nullable = false, updatable = false, insertable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name="sponsor_id",table="prs_sor_role_records")
    private Person sponsor;

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
    private Type affiliationType;

    // TODO map type
    private Type personStatus;

    @Embedded
    private JpaLeaveImpl leave = new JpaLeaveImpl();

    @Embedded
    private JpaActiveImpl active = new JpaActiveImpl();

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false, table="prc_sor_persons")
    private Person person;

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
        this.sponsor = sponsor;
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
        this.personStatus = personStatus;
    }

    public Leave getLeave() {
        return this.leave;
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
}
