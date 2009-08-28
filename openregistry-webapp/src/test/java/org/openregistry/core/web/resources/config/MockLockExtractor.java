package org.openregistry.core.web.resources.config;

import java.security.Principal;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class MockLockExtractor implements LockExtractor {

    private int count = 0;

    public String extract(final Principal principal, final String lockValue) {
        if (count <= 1) {
            count ++;
            return "foo";
        }

        return "foo2";
    }
}
