package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.*;

import java.util.List;

/**
 * Role entity mapped to a persistence store with JPA annotations.
 *
 * @since 1.0
 *        TODO: add all the properties, dependencies and map to OR DB with JPA annotations
 */
public class JpaRoleImpl extends Entity implements Role {

    private List<JpaUrlImpl> urls;

    private List<JpaEmailAddressImpl> emailAddresses;

    private List<JpaPhoneImpl> phones;

    private List<JpaAddressImpl> addresses;

    private String title;

    private Person sponsor;

    private int percentage;

    private String localCode;

    private JpaCampusImpl campus;

    private JpaDepartmentImpl department;

    private Type affiliationType;

    private Type personStatus;

    private JpaLeaveImpl leave = new JpaLeaveImpl();

    private JpaActiveImpl active = new JpaActiveImpl();

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
