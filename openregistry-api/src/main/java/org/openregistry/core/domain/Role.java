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
package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openregistry.core.domain.sor.SorSponsor;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends RoleInfo, Serializable, MutableDateRange {

    Long getId();

    Set<Address> getAddresses();

    Sponsor setSponsor();
    
    Sponsor addSponsor(SorSponsor sorSponsor);
    
    Sponsor getSponsor();

    int getPercentage();

    void setPercentage(int percentage);

    Type getPersonStatus();

    void setPersonStatus(Type personStatus);

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    Set<Url> getUrls();

    Address addAddress();
    
    Address addAddress(Address sorAddress);

    Url addUrl();
    
    Url addUrl(Url sorUrl);

    EmailAddress addEmailAddress();
    
    EmailAddress addEmailAddress(EmailAddress sorEmailAddress);

    Phone addPhone();
    
    Phone addPhone(Phone sorPhone);

    Set<Leave> getLeaves();

    String getLocalCode();

    Type getTerminationReason();

    void setTerminationReason(Type reason);

    /**
     * Determine if this role has been terminated for an associated Person
     * 
     * @return true if the role is terminated, false otherwise, i.e. the role is active
     */
    boolean isTerminated();

    /**
     * Sets the sor role Id to create the link between the calculated role and the Sor Role.
     * @param sorRoleId the role Id.  CANNOT be null.
     */
    void setSorRoleId(Long sorRoleId);

    /**
     * Returns the Sor role identifier, if one has been set.  If there is a calculated role, this MUST be set.
     * @return the role id, of the Sor Role.
     */
    Long getSorRoleId();

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
    

}
