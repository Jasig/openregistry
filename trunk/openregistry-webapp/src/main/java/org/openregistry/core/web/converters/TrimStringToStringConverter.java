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

package org.openregistry.core.web.converters;

import org.springframework.binding.convert.converters.Converter;

/**
 * Converts an empty String that is passed in to a NULL object.
 *
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class TrimStringToStringConverter implements Converter {
    @Override
    public Class getSourceClass() {
        return String.class;
    }

    @Override
    public Class getTargetClass() {
        return String.class;
    }

    @Override
    public Object convertSourceToTargetClass(final Object o, final Class aClass) throws Exception {
        if (o == null) {
            return o;
        }

        final String s = (String) o;
        if (s.isEmpty()) {
            return null;
        }

        return s.trim();
    }
}
