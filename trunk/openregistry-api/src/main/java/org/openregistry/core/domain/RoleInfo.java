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

/**
 * Represents the specific role that a person can hold.  The {@link org.openregistry.core.domain.Role} class is an
 * "instance" of the RoleInfo with additional information (such as Start Date).
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface RoleInfo extends Serializable {

    /**
     * Returns the title of the Role.
     *
     * @return the title.  CANNOT be NULL.
     */
    String getTitle();

    /**
     * The organizational unit that this role belongs to.
     *
     * @return the organizational unit. CANNOT be NULL.
     */
    OrganizationalUnit getOrganizationalUnit();

    /**
     * The campus this role is affiliated with.
     *
     * @return the campus.  CANNOT be NULL.
     */
    Campus getCampus();

    /**
     * The affiliation of this role (i.e. Student, Staff, Faculty, etc.)
     *
     * @return the affiliation.  CANNOT be NULL.
     */
    Type getAffiliationType();

    /**
     * The local code representing this role.
     * @return the local code.
     */
    String getCode();

    /**
     * A name to display for the roleinfo.
     * @return
     */
    String getDisplayableName();

    Long getId();
}
