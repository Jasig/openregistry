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

    SorPerson getPerson();

    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    Map<IdentifierType, String> getIdentifiersByType();

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
