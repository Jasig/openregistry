package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends Serializable {

    Type getAffiliationType();

    List<? extends Address> getAddresses();

    void setSponsor(Person sponsor);
    
    Person getSponsor();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);

    List<? extends Phone> getPhones();

    List<? extends EmailAddress> getEmailAddresses();

    List<? extends Url> getUrls();

    Address addAddress();

    Url addUrl();

    EmailAddress addEmailAddress();

    Phone addPhone();

    List<? extends Leave> getLeaves();

    Active getActive();

    String getTitle();

    Department getDepartment();

    Campus getCampus();

    String getLocalCode();

    Type getAfilliationType();
}
