package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.IdentifierType;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.core.ValidateDefinition;
import org.springframework.core.style.ToStringCreator;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@ValidateDefinition
public final class JpaReconciliationCriteriaImpl implements ReconciliationCriteria {

    @NotNull
    private SorPerson person = new JpaSorPersonImpl();

    private Map<IdentifierType, String> identifiersByType = new HashMap<IdentifierType, String>();

    private String emailAddress;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String region;

    private String postalCode;

    public String toString() {
        final ToStringCreator toStringCreator = new ToStringCreator(this);
        toStringCreator.append("person", this.person);
        toStringCreator.append("emailAddress", this.emailAddress);
        toStringCreator.append("phoneNumber", this.phoneNumber);
        toStringCreator.append("addressLine1", this.addressLine1);
        toStringCreator.append("addressLine2", this.addressLine2);
        toStringCreator.append("city", this.city);
        toStringCreator.append("region", this.region);
        toStringCreator.append("postalCode", this.postalCode);

        return toStringCreator.toString();
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SorPerson getPerson() {
        return this.person;
    }

    public Map<IdentifierType, String> getIdentifiersByType() {
        return this.identifiersByType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
