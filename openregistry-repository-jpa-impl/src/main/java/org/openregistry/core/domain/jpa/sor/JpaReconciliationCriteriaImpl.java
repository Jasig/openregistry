/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.annotation.Required;
import org.openregistry.core.domain.annotation.RequiredSize;
import org.openregistry.core.domain.sor.ReconciliationCriteria;
import org.openregistry.core.domain.sor.SorPerson;
import org.openregistry.core.domain.IdentifierType;
import org.springframework.core.style.ToStringCreator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.HashMap;

/**
 *
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class JpaReconciliationCriteriaImpl implements ReconciliationCriteria {

    @NotNull
    @Valid
    private JpaSorPersonImpl sorPerson = new JpaSorPersonImpl();

    // TODO annotation here for required size?
    private Map<IdentifierType, String> identifiersByType = new HashMap<IdentifierType, String>();

    @Required(property="reconciliation.emailAddress")
    private String emailAddress;

    @Required(property="reconciliation.phoneNumber")
    private String phoneNumber;

    @Required(property = "reconciliation.addressLine1")
    private String addressLine1;

    private String addressLine2;

    @Required(property = "reconciliation.city")
    private String city;

    @Required(property = "reconciliation.region")
    private String region;

    @Required(property = "reconciliation.postalCode")
    private String postalCode;

    public String toString() {
        final ToStringCreator toStringCreator = new ToStringCreator(this);
        toStringCreator.append("person", this.sorPerson);
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
    public SorPerson getSorPerson() {
        return this.sorPerson;
    }

    public Map<IdentifierType, String> getIdentifiersByType() {
        return this.identifiersByType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaReconciliationCriteriaImpl)) return false;

        JpaReconciliationCriteriaImpl that = (JpaReconciliationCriteriaImpl) o;

        if (addressLine1 != null ? !addressLine1.equals(that.addressLine1) : that.addressLine1 != null) return false;
        if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (identifiersByType != null ? !identifiersByType.equals(that.identifiersByType) : that.identifiersByType != null)
            return false;
        if (sorPerson != null ? !sorPerson.equals(that.sorPerson) : that.sorPerson != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sorPerson != null ? sorPerson.hashCode() : 0;
        result = 31 * result + (identifiersByType != null ? identifiersByType.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (addressLine1 != null ? addressLine1.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }
}
