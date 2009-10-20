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

import org.openregistry.core.domain.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 11:00:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SorRole extends RoleInfo, Serializable, MutableDateRange {

    Long getId();
    /**
     * Identifier as assigned from the System of Record
     *
     * @return the identifier from the system of record.  CANNOT be null.
     */
    String getSorId();

    /**
     * Sets the Identifier for this Role WITHIN the system of record.
     * @param id the identifier. CANNOT be null, if setting.
     */
    void setSorId(String id);

    /**
     * The identifier for the System of Record that is asserting this role.  CANNOT be null.
     *
     * @return the identifier for the System of Record
     */
    String getSourceSorIdentifier();

    /**
     * Sets the identifier for the system of record.
     *
     * @param sorIdentifier the system of record identifier.
     */
    void setSourceSorIdentifier(String sorIdentifier);

    /**
     * Expires the System of Record Role NOW with the provided reason.
     *
     * @param terminationReason the reason for termination.
     */
    void expireNow(Type terminationReason);

    /**
     * Expires the System of Record role with the provided reason on the provided date.
     *
     * @param terminationReason the reason for termination.
     * @param expirationDate the date the expiration is effective.
     */
    void expire(Type terminationReason, Date expirationDate);


    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);
    
    RoleInfo getRoleInfo();

    SorSponsor setSponsor();

    SorSponsor getSponsor();

    Type getTerminationReason();

    void setTerminationReason(Type reason);
    
    List<Address> getAddresses();
    
    Address addAddress();

    Address removeAddressById(final Long id);

    Address getAddress(long id);

    List<Phone> getPhones();

    List<EmailAddress> getEmailAddresses();

    EmailAddress removeEmailAddressById(final Long id);

    EmailAddress addEmailAddress();

    Phone addPhone();

    Phone removePhoneById(final Long id);

    List<Url> getUrls();

    Url addUrl();

    Url removeURLById(final Long id);

    List<Leave> getLeaves();

    OrganizationalUnit getOrganizationalUnit();

    Campus getCampus();

    String getLocalCode();
 
}
