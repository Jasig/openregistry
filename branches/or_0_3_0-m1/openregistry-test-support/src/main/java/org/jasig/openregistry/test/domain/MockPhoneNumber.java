package org.jasig.openregistry.test.domain;

import org.openregistry.core.domain.ContactPhone;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.Type;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockPhoneNumber implements ContactPhone {

    private Long id;

    private String extension;

    private String areaCode;

    private String number;

    private Type addressType;

    private Type phoneType;

    private String countryCode;

    public Type getAddressType() {
        return addressType;
    }

    public void setAddressType(final Type addressType) {
        this.addressType = addressType;
    }

    public Type getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(final Type phoneType) {
        this.phoneType = phoneType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(final String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @Override
    public void clear() {
        this.number = null;
        this.areaCode = null;
        this.extension = null;
        this.countryCode = null;
        this.phoneType = null;
        this.addressType = null;
    }

    @Override
    public void update(final Phone phone) {
        if (phone == null) {
            clear();
            return;
        }

        this.number = phone.getNumber();
        this.areaCode = phone.getAreaCode();
        this.extension = phone.getExtension();
        this.countryCode = phone.getCountryCode();
        this.phoneType = phone.getPhoneType();
        this.addressType = phone.getAddressType();
    }
}
