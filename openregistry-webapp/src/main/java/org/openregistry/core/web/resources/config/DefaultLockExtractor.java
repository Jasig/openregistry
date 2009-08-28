package org.openregistry.core.web.resources.config;

import java.security.Principal;

/**
 * Extracts the Principal name from the Principal.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class DefaultLockExtractor implements LockExtractor {

    public String extract(final Principal principal, final String lockValue) {
        return principal.getName();
    }
}
