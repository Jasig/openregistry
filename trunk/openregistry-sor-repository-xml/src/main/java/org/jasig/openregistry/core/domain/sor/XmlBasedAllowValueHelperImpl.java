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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class XmlBasedAllowValueHelperImpl {

    @XmlElement(name="property",nillable = false,required = true)
    private String property;

    @XmlElementWrapper(name = "values", required = true)
    @XmlElement(name = "value", nillable = false)
    private HashSet<String> allowedValues = new HashSet<String>();

    public void setDomainProperty(final String property) {
        this.property = property;
    }

    public void setAllowedValues(final HashSet<String> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public boolean isAllowedValueForProperty(final String property, final String value) {
        return allowedValues.isEmpty() || allowedValues.contains(value);
    }

    public boolean supportsProperty(final String property) {
        return this.property.equals(property);
    }
}
