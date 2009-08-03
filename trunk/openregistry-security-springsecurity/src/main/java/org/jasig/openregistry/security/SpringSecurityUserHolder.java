package org.jasig.openregistry.security;

import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.Authentication;
import org.springframework.security.providers.cas.CasAuthenticationToken;

/**
 * Exposes the Spring User to be retrieved.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public final class SpringSecurityUserHolder {

    private SpringSecurityUserHolder() {
        // nothing to do
    }

    public static SpringSecurityUserImpl getSpringSecurityUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();

        if (authentication instanceof org.springframework.security.providers.cas.CasAuthenticationToken) {
            return (SpringSecurityUserImpl) ((CasAuthenticationToken) authentication).getUserDetails();
        }
        final Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            throw new IllegalStateException("No User Found");
        }

        return (SpringSecurityUserImpl) principal;
    }
}
