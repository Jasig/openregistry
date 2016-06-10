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

package org.openregistry.core.domain;

import java.io.Serializable;
import java.util.Set;

import org.openregistry.core.domain.sor.SorRole;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends Serializable, DateRange, Comparable<Role> {

    Set<Address> getAddresses();

    Long getSponsorId();

    Type getSponsorType();

    void setSponsorType(Type sponsorType);

    void setSponsorId(Long sponsorId);

    int getPercentage();

    Type getPersonStatus();

    String getTitle();

    void setTitle(String title);

    OrganizationalUnit getOrganizationalUnit();

    void setOrganizationalUnit(OrganizationalUnit organizationalUnit);

    Type getAffiliationType();

    void setAffiliationType(Type affiliationType);

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    Set<Url> getUrls();

    Set<Leave> getLeaves();

    Type getTerminationReason();

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

    /**
     * Returns the Sor role identifier, if one has been set.  If there is a calculated role, this MUST be set.
     * @return the role id, of the Sor Role.
     */
    Long getSorRoleId();


    String getPHI();

    void setPHI(String PHI);

    String getRBHS();

    void setRBHS(String RBHS);

    /**
     * Expires the System of Record Role NOW with the provided reason.
     *
     * @param terminationReason the reason for termination.
     * @param orphaned true if the SoRRole is actually being deleted.
     */
    void expireNow(Type terminationReason, boolean orphaned);

    void recalculate(SorRole sorRole);
}
