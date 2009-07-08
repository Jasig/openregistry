package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Set;

import org.openregistry.core.domain.sor.SorSponsor;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends Serializable, MutableDateRange {

    Long getId();

    Type getAffiliationType();

    Set<Address> getAddresses();

    Sponsor setSponsor();
    
    Sponsor addSponsor(SorSponsor sorSponsor);
    
    Sponsor getSponsor();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);
    
    RoleInfo getRoleInfo();

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    Set<Url> getUrls();

    Address addAddress();
    
    Address addAddress(Address sorAddress);

    Url addUrl();
    
    Url addUrl(Url sorUrl);

    EmailAddress addEmailAddress();
    
    EmailAddress addEmailAddress(EmailAddress sorEmailAddress);

    Phone addPhone();
    
    Phone addPhone(Phone sorPhone);

    Set<Leave> getLeaves();

    String getTitle();

    OrganizationalUnit getOrganizationalUnit();

    Campus getCampus();

    String getLocalCode();

    Type getTerminationReason();

    void setTerminationReason(Type reason);

    /**
     * Determine if this role has been terminated for an associated Person
     * 
     * @return true if the role is terminated, false otherwise, i.e. the role is active
     */
    boolean isTerminated();
}
