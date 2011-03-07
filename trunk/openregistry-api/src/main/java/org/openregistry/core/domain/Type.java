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

/**
 * Represents the various "types" that can be in the system.  Examples include "Termination", which would include
 * descriptions such as "Fired", "Retired", "Terminated", etc. or "Address" such as "Campus", "Home", "Office", etc.
 * <p>
 * The System defines particular types, enumerated in the {@link org.openregistry.core.domain.Type.DataTypes} enumeration.
 * However, the system is flexible enough that other types can be created and used.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Type extends Serializable {

    /**
     * The default list of enumerations that the system understands.
     */
    enum DataTypes {ADDRESS, AFFILIATION, CAMPUS, LEAVE, NAME, ORGANIZATIONAL_UNIT, PERSON, 
    	PHONE, SPONSOR, STATUS, TERMINATION, URL}

    enum TerminationTypes {FIRED, EXPIRED, UNSPECIFIED}

    enum SponsorTypes {PERSON, ORG_UNIT}

    enum PersonStatusTypes {ACTIVE, INACTIVE, LEAVE_OF_ABSENCE, DECEASED, TERMINATED, SUSPENDED, SHORT_WORK_BREAK, LEAVE_WITH_PAY, RETIRED}

    enum AddressTypes {CAMPUS, HOME, OFFICE}

    enum PhoneTypes {LANDLINE, CELL, PAGER, FAX, OTHER}

    enum NameTypes {LEGAL, FORMAL, AKA, MAIDEN, FKA}

    enum AffiliationTypes {FACULTY, STAFF, STUDENT, ADMIT_COMING, SUMMER_STUDENT, WINTER_STUDENT, STUDENT_WORKER, CE_STUDENT, GUEST, ALUM, RETIREE, FOUNDATION}

    /**
     * The internal identifier of the type.
     *
     * @return the internal identifier of the type.  CANNOT be NULL.
     */
    Long getId();

    /**
     * The specific data type.  Should be one of the enumerated values, but does not have to be.
     *
     * @return the data type. CANNOT be NULL.
     */
    String getDataType();

    /**
     * The textual description, for human readability.
     * 
     * @return the textual description. CANNOT be NULL.
     */
    String getDescription();

    boolean isSameAs(Type other);
    
}
