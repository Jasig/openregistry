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
