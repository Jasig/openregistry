package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface EmailAddress {

    Type getAddressType();

    String getAddress();

    void setAddress(String address);

    void setAddressType(Type type);
}
