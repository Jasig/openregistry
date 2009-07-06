package org.openregistry.security;

import org.openregistry.core.domain.Person;

import java.util.List;

/**
 * Allows one to retrieve the permissions for a particular user.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PermissionRepository {

    /**
     * Returns various permissions for a particular permission type.  Doesn't really make sense to use for things such
     * as USER or EXPRESSION.
     *
     * @param permissionType the permission type to retrieve permissions for.
     * @return the list.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<Permission> getPermissionsFor(Permission.PermissionType permissionType);

    /**
     * Returns all of the permissions for a particular user.  This includes the following:
     * <ul>
     * <li>All AUTHENTICATED permissions</li>
     * <li>All USERNAME permissions (for that user)
     * @param username the username provided by the user. CANNOT be NULL.
     * @param person the person, *iif* they exist.  CAN be NULL.
     * @return the list of permissions.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<Permission> getPermissionsForUser(final String username, final Person person);
}
