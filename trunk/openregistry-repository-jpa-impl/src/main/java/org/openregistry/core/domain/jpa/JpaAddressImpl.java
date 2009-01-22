package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaAddressImpl extends Entity implements Address {

    private Type type;

    private String line1;

    private String line2;

    private String line3;

    private JpaRegionImpl jpaRegion;

    private JpaCountryImpl jpaCountry;

    private String city;

    private String postalCode;

    public Type getType() {
        return this.type;
    }

    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public Region getRegion() {
        return this.jpaRegion;
    }

    public Country getCountry() {
        return this.jpaCountry;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public void setLine1(final String line1) {
        this.line1 = line1;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public void setRegion(final Region region) {
        if (!(region instanceof JpaRegionImpl)) {
            throw new IllegalStateException("Region implementation must be of type JpaRegionImpl");
        }
        this.jpaRegion = (JpaRegionImpl) region;
    }

    public void setCountry(final Country country) {
        if (!(country instanceof JpaCountryImpl)) {
            throw new IllegalStateException("Country implementation must be of type JpaCountryImpl");
        }
        this.jpaCountry = (JpaCountryImpl) country;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
}
