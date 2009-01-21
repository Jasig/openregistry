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
}
