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

package org.openregistry.core.service;

import org.openregistry.core.domain.sor.SorPerson;

import java.util.List;

/**
 * Simple implementation of a way to select ssn.
 *
 * @version $Revision$ $Date$
 * @since 0.1-M1
 */
public final class DefaultSSNFieldElector implements FieldElector<String> {

    @Override
    public String elect(final SorPerson newestPerson, final List<SorPerson> sorPersons, final boolean deleted) {
        if (newestPerson != null && newestPerson.getSsn() != null) {
            return newestPerson.getSsn();
        }

        if (sorPersons.isEmpty()) {
            return null;
        }

        return sorPersons.get(0).getSsn();
    }
}