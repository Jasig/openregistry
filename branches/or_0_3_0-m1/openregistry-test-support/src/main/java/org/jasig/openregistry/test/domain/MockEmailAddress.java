package org.jasig.openregistry.test.domain;

import org.openregistry.core.domain.ContactEmailAddress;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Type;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockEmailAddress implements ContactEmailAddress {

    private Type type;

    private String address;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Type getAddressType() {
        return this.type;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public void setAddressType(final Type type) {
        this.type = type;
    }

    @Override
    public void clear() {
        this.type = null;
        this.address = null;
    }

    @Override
    public void update(final EmailAddress emailAddress) {
        if (emailAddress != null) {
            this.type = emailAddress.getAddressType();
            this.address = emailAddress.getAddress();
            return;
        }

        clear();
    }
}
