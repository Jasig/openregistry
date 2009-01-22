package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Address {

    Type getType();

    String getLine1();

    String getLine2();

    String getLine3();

    Region getRegion();

    Country getCountry();

    String getCity();

    String getPostalCode();

    void setType(Type type);

    void setLine1(String line1);

    void setLine2(String line2);

    void setLine3(String line3);

    void setRegion(Region region);

    void setCountry(Country country);

    void setCity(String city);

    void setPostalCode(String postalCode);
}
