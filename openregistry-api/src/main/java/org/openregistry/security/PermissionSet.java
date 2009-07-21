package org.openregistry.security;

import java.util.Set;

/**
 * 
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface PermissionSet {

    String getName();

    Set<Permission> getPermissions();

}
