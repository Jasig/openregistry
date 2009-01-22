package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Type;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaPhoneImpl extends Entity implements Phone {

    private Type addressType;

    private Type phoneType;

    private String countryCode;

    private String areaCode;

    private String number;

    private String extension;

    public Type getAddressType() {
        return this.addressType;
    }

    public Type getPhoneType() {
        return this.phoneType;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getNumber() {
        return this.number;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setAddressType(final Type addressType) {
        this.addressType = addressType;
    }

    public void setPhoneType(final Type phoneType) {
        this.phoneType = phoneType;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }
}
