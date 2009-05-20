package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.IdentifierType;

import java.util.Map;
import java.io.Serializable;

/**
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationCriteria extends Serializable {

    SorPerson getPerson();

    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    Map<IdentifierType, String> getIdentifiersByType();

    String getAddressLine1();

    String getAddressLine2();

    String getCity();

    String getRegion();

    String getPostalCode();

    void setAddressLine1(String addressLine1);

    void setAddressLine2(String addressLine2);

    void setCity(String city);

    void setRegion(String region);

    void setPostalCode(String postalCode);

}
