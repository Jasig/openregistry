package org.jasig.openregistry.security;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import org.openregistry.security.Privilege;
import org.openregistry.security.User;
import org.openregistry.security.SystemOfRecord;

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

    public SpringSecurityUserImpl(final String username, final boolean enabled, final Set<Privilege> permissions) {
        this.username = username;
        this.enabled = enabled;
        this.permissions = permissions;

        for (final Privilege r : permissions) {
            this.systemOfRecords.add(r.getSystemOfRecord());
        }
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
