/**
 * Copyright (C) 2009 Jasig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
