package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Phone extends Serializable {

    Long getId();
    
    Type getAddressType();

    Type getPhoneType();

    String getCountryCode();

    String getAreaCode();

    String getNumber();

    String getExtension();

    void setAddressType(Type addressType);

    void setPhoneType(Type phoneType);

    void setCountryCode(String countryCode);

    void setAreaCode(String areaCode);

    void setNumber(String number);

    void setExtension(String extension);
}
