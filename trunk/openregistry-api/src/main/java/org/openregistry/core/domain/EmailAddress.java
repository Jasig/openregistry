package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface EmailAddress extends Serializable {

    Long getId();
    
    Type getAddressType();

    String getAddress();

    void setAddress(String address);

    void setAddressType(Type type);
}
