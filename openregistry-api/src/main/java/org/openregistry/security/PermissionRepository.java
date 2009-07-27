package org.openregistry.security;

import java.util.List;

/**
 * Allows one to retrieve the permissions for a particular user.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PermissionRepository {

    /**
     * Returns various privileges for a particular permission type.  Doesn't really make sense to use for things such
     * as USER.  If you retrieve EXPRESSIONs, one needs to parse them after.
     *
     * @param subjectType the permission type to retrieve permissions for.
     * @return the list.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<Privilege> getPrivilegesFor(Subject.SubjectType subjectType);

    /**
     * Returns the various privilege sets for a particular permission type.  Doesn't really make sense for things like USER.
     * If you retrieve EXPRESSIONs, one needs to parse them after.
     * @param subjectType the permission type to retrieve permissions for.
     * @return the list.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<PrivilegeSet> getPrivilegeSetsFor(Subject.SubjectType subjectType);

    /**
     * Returns all of the privilege sets for a particular user that match PermissionType = USER.
     *
     * @param username the username provided by the user. CANNOT be NULL.
     * @return the list of privilege sets.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<PrivilegeSet> getPrivilegeSetsForUser(String username);

    /**
     * Returns all of the privileges for a particular user that match PermissionType = USER.
     *
     * @param username the username provided by the user. CANNOT be NULL.
     * @return the list of permissions.  CANNOT be NULL.  CAN be EMPTY.
     */
    List<Privilege> getPrivilegesForUser(final String username);
}
