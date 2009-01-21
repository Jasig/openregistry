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

    private List<JpaEmailAddress> emailAddresses;

    public List<Address> getAddresses() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Phone> getPhones() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<? extends EmailAddress> getEmailAddresses() {
        return this.emailAddresses;
    }

    public List<? extends Url> getUrls() {
        return this.urls;
    }

    public Address addAddress() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Url addUrl() {
        final JpaUrlImpl url = new JpaUrlImpl();
        this.urls.add(url);
        return url;
    }

    public EmailAddress addEmailAddress() {
        final JpaEmailAddress jpaEmailAddress = new JpaEmailAddress();
        this.emailAddresses.add(jpaEmailAddress);
        return jpaEmailAddress;
    }

    public Phone addPhone() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getTitle() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
