/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.audit;

import org.hibernate.envers.RevisionListener;

/**
 * Sets the username from the Spring SecurityContext.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class SpringSecurityListener implements RevisionListener {

    public void newRevision(final Object o) {
        final SpringSecurityRevisionEntity entity = (SpringSecurityRevisionEntity) o;
        // final SecurityContext context = SecurityContextHolder.getContext();

        entity.setUsername("anonymous");
        /*
        if (context != null) {
            if (context.getAuthentication() != null) {
                entity.setUsername(context.getAuthentication().getName());
            } else {
                entity.setUsername("anonymous");
            }
        } */
    }
}
