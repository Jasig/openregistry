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

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Identifier;

import java.util.Date;

/**
 * Handles when one of the identifiers changes and third-parties need to be notified.
 *
 * @since 1.0
 */
public interface IdentifierChangeService {
    /**
     * changes the net id
     * this assumes that provided internalID is the primary Identifier of a peron
     * @param internalId existing  PRIMARY identifier object associated with person     *
     * @param changedId value of new identifier
     * @return
     */

    boolean change( Identifier internalId,  String changedId);

    /**
    /**
     * Update the changeable flag of the identififer
     * @param identifierToChg
     * @param changeable
     */
    void updateChangeable(final Identifier identifierToChg,  boolean changeable);

    /**
     * Update the change expiration date of the identifier
     * @param identifierToChg
     * @param changeExpDate
     */
    void updateChangeExpDate(final Identifier identifierToChg, Date changeExpDate);
}
