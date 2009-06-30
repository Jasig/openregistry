package org.jasig.openregistry.core.security;

import org.springframework.security.GrantedAuthority;
import org.openregistry.security.Permission;

/**
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityPermissionImpl implements Permission, GrantedAuthority {

    private final String systemOfRecord;

    private final Interface[] interfaces;

    private final String permission;

    public SpringSecurityPermissionImpl(final String systemOfRecord, final Interface[] interfaces, final String permission) {
        this.systemOfRecord = systemOfRecord;
        this.interfaces = interfaces;
        this.permission = permission;
    }

    public String getSystemOfRecord() {
        return this.systemOfRecord;
    }

    public Interface[] getInterfaces() {
        return this.interfaces;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getAuthority() {
        return this.permission;
    }

    public int compareTo(final Object o) {
        final GrantedAuthority g = (GrantedAuthority) o;
        return this.permission.compareTo(g.getAuthority());
    }
}
