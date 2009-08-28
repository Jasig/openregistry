package org.openregistry.core.web.resources.config;

import java.security.Principal;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public interface LockExtractor {

    /**
     * Extracts the lock from either the principal or the lock value provided (or some other method, combining the two).
     *
     * @param principal the principal to extract the lock from.
     * @param lockValue the potential value of the lock.
     * @return the lock.  CANNOT be NULL.
     */
    String extract(Principal principal, String lockValue);
}
