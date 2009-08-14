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
package org.openregistry.security;

import java.util.Set;

/**
 * Represents a set of rules and the person or thing they apply to.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PrivilegeSet extends PermissionSet, Subject {

    /**
     * Consolidates the privileges held by a privileges into the set of privileges.
     * @return the set of privileges. CANNOT be NULL.  CAN be EMPTY, though that doesn't make much sense.
     */
    Set<Privilege> getPrivileges();
}
