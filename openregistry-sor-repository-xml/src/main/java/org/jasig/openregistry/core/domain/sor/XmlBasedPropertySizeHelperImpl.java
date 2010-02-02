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
package org.jasig.openregistry.core.domain.sor;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Collection;

/**
 * Helper class to hold the min and max values for collections.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class XmlBasedPropertySizeHelperImpl {

    @XmlAttribute(name="min")
    private int min = 0;

    @XmlAttribute(name="max")
    private int max = Integer.MAX_VALUE;

    @XmlAttribute(name = "property", required = true)
    private String property;

    public void setProperty(final String property) {
        this.property = property;
    }

    public void setMax(final int max) {
        this.max = max;
    }

    public void setMin(final int min) {
        this.min = min;
    }

    public boolean supportsProperty(final String property) {
        return this.property.equals(property);
    }

    public boolean isWithinRequiredRangeForProperty(final Collection collection) {
        return collection.size() >= this.min && collection.size() <= this.max;
    }
}
