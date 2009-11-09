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
package org.openregistry.core.web.resources.config;

import java.security.Principal;

/**
 * Strategy for determining how locks are created for the RESTful API for the Activation Keys.
 *
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
