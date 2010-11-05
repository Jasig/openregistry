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

import org.openregistry.core.domain.Campus;
import org.openregistry.core.repository.ReferenceRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 28, 2009
 * Time: 3:33:37 PM
 * To change this template use File | Settings | File Templates.
 */
public final class CampusConverter extends AbstractConverter {

    public CampusConverter(final ReferenceRepository referenceRepository) {
       super(Campus.class, referenceRepository);
   }

    @Override
    protected Object toObject(final String string, final Class targetClass) throws Exception {
        return getReferenceRepository().getCampusById(new Long(string));
    }

    @Override
    protected String getToStringInternal(final Object o) {
        return Long.toString(((Campus) o).getId());
    }
}
