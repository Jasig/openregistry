package org.jasig.openregistry.core.security;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import org.openregistry.core.domain.Person;
import org.openregistry.security.Permission;

import java.util.List;

/**
 * Implementation of the Spring Security {@link org.springframework.security.userdetails.UserDetails}
 * interface. This allows for the usage of Spring Security within the OR system.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityUserImpl implements UserDetails {

    private final String username;

    private final boolean enabled;

    private final List<Permission> permissions;

    public SpringSecurityUserImpl(final String username, final boolean enabled, final List<Permission> permissions) {
        this.username = username;
        this.enabled = enabled;
        this.permissions = permissions;
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
