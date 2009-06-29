package org.openregistry.security;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;

import java.util.Set;
import java.util.Date;
import java.util.Collections;

/**
 * Implementation of the {@link org.openregistry.security.User} interface as well as the Spring Security {@link org.springframework.security.userdetails.UserDetails}
 * interface. This allows for the usage of Spring Security within the OR system.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityUserImpl implements User, UserDetails {

    private final String nickName;

    private final Set<SoRInterface> systemOfRecords;

    private final Date lastLoggedIn;

    private final String lastLoggedInHost;

    private final String username;

    private final boolean enabled;

    public SpringSecurityUserImpl(final String username, final String nickname, final Set<SoRInterface> systemOfRecords, final Date lastLoggedIn, final String lastLoggedInHost, final boolean enabled) {
        this.username = username;
        this.nickName = nickname;
        this.systemOfRecords = Collections.unmodifiableSet(systemOfRecords);
        this.lastLoggedIn = lastLoggedIn;
        this.lastLoggedInHost = lastLoggedInHost;
        this.enabled = enabled;
    }

    public String getNickName() {
        return this.nickName;
    }

    public Set<SoRInterface> getSystemOfRecords() {
        return this.systemOfRecords;
    }

    public Date getLastLoggedIn() {
        return this.lastLoggedIn;
    }

    public String getLastLoggedInHost() {
        return this.lastLoggedInHost;
    }

    // TODO fill this in
    public GrantedAuthority[] getAuthorities() {
        return new GrantedAuthority[0];  //To change body of implemented methods use File | Settings | File Templates.
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
