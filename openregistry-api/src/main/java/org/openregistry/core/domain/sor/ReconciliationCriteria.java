/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.domain.sor;

import org.openregistry.core.domain.IdentifierType;

import java.util.Map;
import java.io.Serializable;

/**
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface ReconciliationCriteria extends Serializable {

    /**
     * The System of Record person we are trying to add.
     *
     * @return the System of Record Person.  CANNOT be NULL.
     */
    SorPerson getSorPerson();

    void setSorPerson(SorPerson sorPerson);

    /**
     * An email address that MIGHT be used for reconciliation.
     *
     * @return the email address or null if it doesn't work.
     */
    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    String getPhoneCountryCode();

    void setPhoneCountryCode(String phoneCountryCode);
    
    String getPhoneAreaCode();

    void setPhoneAreaCode(String phoneAreaCode);
    
    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);
    
    String getPhoneExtension();

    void setPhoneExtension(String phoneExtension);

    Map<IdentifierType, String> getIdentifiersByType();

    void setIdentifiersByType(Map<IdentifierType, String> identifiersByType);

    String getAddressLine1();

    String getAddressLine2();

    String getCity();

    String getRegion();

    String getPostalCode();

    void setAddressLine1(String addressLine1);

    void setAddressLine2(String addressLine2);

    void setCity(String city);

    void setRegion(String region);

    void setPostalCode(String postalCode);

}
