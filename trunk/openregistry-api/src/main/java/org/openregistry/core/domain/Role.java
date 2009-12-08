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
import java.util.Set;

import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;

/**
 * Entity representing canonical roles associated with resources and persons in the Open Registry system
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface Role extends RoleInfo, Serializable, DateRange {

    Set<Address> getAddresses();

    Sponsor getSponsor();

    int getPercentage();

    Type getPersonStatus();

    Set<Phone> getPhones();

    Set<EmailAddress> getEmailAddresses();

    Set<Url> getUrls();

    Set<Leave> getLeaves();

    String getLocalCode();

    Type getTerminationReason();

    /**
     * Determine if this role has been terminated for an associated Person
     * 
     * @return true if the role is terminated, false otherwise, i.e. the role is active
     */
    boolean isTerminated();

    /**
     * Returns the Sor role identifier, if one has been set.  If there is a calculated role, this MUST be set.
     * @return the role id, of the Sor Role.
     */
    Long getSorRoleId();

    /**
     * Expires the System of Record Role NOW with the provided reason.
     *
     * @param terminationReason the reason for termination.
     * @param orphaned true if the SoRRole is actually being deleted.
     */
    void expireNow(Type terminationReason, boolean orphaned);

    void recalculate(SorRole sorRole);
}
