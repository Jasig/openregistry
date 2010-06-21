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

import org.openregistry.core.domain.*;

import java.io.*;
import java.util.*;

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

    void addOrUpdateEmail(String emailAddress, Type emailType);

    /**
     * Determine if this role has been terminated for an associated Person
     *
     * @return true if the role is terminated, false otherwise, i.e. the role is active
     */
    boolean isTerminated();

    /**
     * Returns true if the start date has not occurred yet.
     * <p>
     * Note, a terminated role will still be denoted as not yet active.
     *
     * @return true if the start date has not occurred yet.  False otherwise.
     */
    boolean isNotYetActive();

    /**
     * Returns true if the current date is between the start and end date, or after the start date if there is no
     * end date yet.
     *
     * @return true if its active, false otherwise.
     */
    boolean isActive();
}
