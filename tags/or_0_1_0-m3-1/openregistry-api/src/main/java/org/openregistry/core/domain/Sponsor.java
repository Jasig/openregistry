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

/**
 * @author Dave Steiner
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Sponsor extends Serializable {
	
	/**
     * Defines the type of Sponsor this is, i.e. person, role, organizational unit, etc.
     * 
     * @return the type.  CANNOT be null.
     */
    Type getType();

    /**
     * The actual Sponsor ID value.
     * 
     * @return the Sponsor ID value.  CANNOT be null.
     */
    Long getSponsorId();

    /**
     * Sets the type of this Sponsor.
     *
     * @param type the type of the Sponsor.  Cannot be NULL.
     */
    void setType(Type type);

    /**
     * Sets the Sponsor ID value.
     * 
     * @param id the Sponsor ID value.  CANNOT be null.
     */
    void setSponsorId(Long id);
    
    /**
     * Returns the set of roles for this Sponsor
     * 
     * @return roles
     */
    Set<Role> getRoles();
    
    /**
     * Add a role to this Sponsor
     * 
     * @param role role to be added
     */
    void addRole(Role role);

}
