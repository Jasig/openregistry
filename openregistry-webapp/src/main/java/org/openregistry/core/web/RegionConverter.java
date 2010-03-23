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

package org.openregistry.core.web;

import org.openregistry.core.domain.Region;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Converts a Region from its code to the actual object, and back.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class RegionConverter extends AbstractConverter {

    public RegionConverter(final ReferenceRepository referenceRepository) {
       super(Region.class, referenceRepository);
   }

    @Override
    protected Object toObject(String string, Class targetClass) throws Exception {
        final String trimmedText = string.trim();
        return getReferenceRepository().getRegionByCodeOrName(trimmedText);
    }

    @Override
    protected String getToStringInternal(final Object o) {
        return ((Region) o).getCode();
    }
}