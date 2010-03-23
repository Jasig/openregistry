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

import org.springframework.binding.convert.converters.StringToObject;
import org.openregistry.core.repository.ReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
public abstract class AbstractConverter extends StringToObject {

    private final ReferenceRepository referenceRepository;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected AbstractConverter(final Class converterClass, final ReferenceRepository referenceRepository) {
        super(converterClass);
        this.referenceRepository = referenceRepository;
    }

    protected final ReferenceRepository getReferenceRepository() {
        return this.referenceRepository;
    }

    @Override
    protected final String toString(final Object o) throws Exception {
        return o == null ? " " : getToStringInternal(o);
    }

    /**
     * Returns the object as a String.
     * @param o the object.  CANNOT be NULL.
     * @return the string.
     */
    protected abstract String getToStringInternal(Object o);
}
