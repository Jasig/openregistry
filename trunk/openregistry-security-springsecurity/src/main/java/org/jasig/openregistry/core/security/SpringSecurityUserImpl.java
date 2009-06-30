package org.jasig.openregistry.core.security;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import org.openregistry.core.domain.Person;
import org.openregistry.security.User;
import org.openregistry.security.Permission;

import java.util.Set;
import java.util.Date;
import java.util.Collections;
import java.util.HashSet;

/**
 * Implementation of the {@link org.openregistry.security.User} interface as well as the Spring Security {@link org.springframework.security.userdetails.UserDetails}
 * interface. This allows for the usage of Spring Security within the OR system.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityUserImpl implements User, UserDetails {

    private final String nickName;

    private final Date lastLoggedIn;

    private final String lastLoggedInHost;

    private final String username;

    private final boolean enabled;

    private final Set<SpringSecurityPermissionImpl> permissions;

    private final Set<Permission> origPermissions;

    private final Person person;

    public SpringSecurityUserImpl(final String username, final String nickname, final Person person, final Set<SpringSecurityPermissionImpl> permissions, final Date lastLoggedIn, final String lastLoggedInHost, final boolean enabled) {
        this.username = username;
        this.nickName = nickname;
        this.permissions = Collections.unmodifiableSet(permissions);
        this.lastLoggedIn = lastLoggedIn;
        this.lastLoggedInHost = lastLoggedInHost;
        this.enabled = enabled;
        this.person = person;

        final Set<Permission> tempSet = new HashSet<Permission>();
        for (final Permission p : this.permissions) {
            tempSet.add(p);
        }

        this.origPermissions = Collections.unmodifiableSet(tempSet);
    }

    public Person getPerson() {
        return this.person;
    }

    public String getNickName() {
        return this.nickName;
    }

    public Date getLastLoggedIn() {
        return this.lastLoggedIn;
    }

    public String getLastLoggedInHost() {
        return this.lastLoggedInHost;
    }

    public GrantedAuthority[] getAuthorities() {
        return this.permissions.toArray(new GrantedAuthority[this.permissions.size()]);
    }

    public Set<Permission> getPermissions() {
        return this.origPermissions;
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
