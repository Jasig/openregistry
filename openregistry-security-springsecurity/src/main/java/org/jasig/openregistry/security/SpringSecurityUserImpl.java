package org.jasig.openregistry.security;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import org.openregistry.security.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of the Spring Security {@link org.springframework.security.userdetails.UserDetails}
 * interface. This allows for the usage of Spring Security within the OR system.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityUserImpl implements UserDetails, User {

    private final String username;

    private final boolean enabled;

    private final Set<Privilege> permissions;

    private final Set<SystemOfRecord> systemOfRecords = new HashSet<SystemOfRecord>();

    private final ExpressionParser expressionParser;

    private SystemOfRecord currentSystemOfRecord = null;

    public SpringSecurityUserImpl(final String username, final boolean enabled, final Set<Privilege> permissions, final ExpressionParser expressionParser) {
        this.username = username;
        this.enabled = enabled;
        this.permissions = permissions;

        for (final Privilege r : permissions) {
            this.systemOfRecords.add(r.getSystemOfRecord());
        }

        this.expressionParser = expressionParser;
    }

    /**
     * Determines whether something has the privilege to do something or not.  We've consolidated this here so that in theory, everything can just call this and if we find a bug
     * its fixable in one place.
     *
     * @param permissionType the type of thing we're trying to do.
     * @param systemOfRecord the system of record this applies to (no system of record implies, we want to do something to a calculated role).
     * @param resource the resource we're attempting to do something to.
     * @return true if we can do it, false otherwise.
     */
    public boolean hasPrivilegeTo(final Permission.PermissionType permissionType, SystemOfRecord systemOfRecord, String resource) {

        boolean hasPrivilege = false;

        for (final Privilege privilege : this.permissions) {
            if ((systemOfRecord == null && privilege.getSystemOfRecord() ==null) || (privilege.getSystemOfRecord() != null && privilege.getSystemOfRecord().equals(systemOfRecord))) {
                // TODO how are we comparing resources?
                final boolean resourceToCompare = this.expressionParser.matches(resource, privilege.getResource());
                if (resourceToCompare) {
                    switch (permissionType) {
                        case CREATE:
                            hasPrivilege = privilege.canCreate();
                            break;

                        case READ:
                            hasPrivilege = privilege.canRead();
                            break;

                        case UPDATE:
                            hasPrivilege = privilege.canUpdate();
                            break;

                        case DELETE:
                            hasPrivilege = privilege.canDelete();
                            break;
                    }
                }
            }

        }

        return hasPrivilege;
    }

    public SystemOfRecord getCurrentSystemOfRecord() {
        return this.currentSystemOfRecord;
    }

    public void setCurrentSystemOfRecord(final SystemOfRecord systemOfRecord) {
        this.currentSystemOfRecord = systemOfRecord;
    }

    public Set<SystemOfRecord> getSystemOfRecords() {
        return this.systemOfRecords;
    }

    // TODO fix
    public GrantedAuthority[] getAuthorities() {
        return new GrantedAuthority[0];
    }

    /**
     * We NEVER store the password.
     * @return null
     */
    public String getPassword() {
        return null;
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * Always returns true because we don't track that information at this level.
     * @return true
     */
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Always returns true because we don't track that information at this level.
     * @return true
     */
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Always returns true because we don't track that information at this level.
     * @return true
     */
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
