package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends Serializable, DateRange {

    Type getAffiliationType();

    Set<? extends Address> getAddresses();

    void setSponsor(Person sponsor);
    
    Person getSponsor();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);

    Set<? extends Phone> getPhones();

    Set<? extends EmailAddress> getEmailAddresses();

    Set<? extends Url> getUrls();

    Address addAddress();

    Url addUrl();

    EmailAddress addEmailAddress();

    Phone addPhone();

    Set<? extends Leave> getLeaves();

    String getTitle();

    Department getDepartment();

    Campus getCampus();

    String getLocalCode();

    Type getTerminationReason();

    void setTerminationReason(Type reason);
}
