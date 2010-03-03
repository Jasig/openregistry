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

import org.openregistry.core.domain.sor.SoRSpecification;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * @version $Revision$ $Date$
 * @since 0.1
 */
@XmlRootElement(name = "specification")
public final class XmlBasedSoRSpecificationImpl implements SoRSpecification {

    @XmlElement(name="id",nillable = false)
    private String sor;

    @XmlElement(name="name", nillable = false)
    private String internalName;

    @XmlElement(name="description",nillable = false)
    private String internalDescription;

    @XmlElementWrapper(name = "requiredProperties", required = false)
    @XmlElement(name = "property",nillable = false)
    private HashSet<String> requiredProperties = new HashSet<String>();

    @XmlElementWrapper(name = "disallowedProperties", required = false)
    @XmlElement(name = "property", nillable = false)
    private HashSet<String> disallowedProperties = new HashSet<String>();

    @XmlElementWrapper(name = "allowedInterfaces",required = true)
    @XmlElement(name = "interface", nillable = false)    
    private HashSet<Interfaces> interfaces = new HashSet<Interfaces>();

    @XmlElementWrapper(name = "notificationSchemes", required= false)
    private Map<Interfaces, String> interfaceToNotificationSchemeMapping = new HashMap<Interfaces, String>();

    @XmlElementWrapper(name = "allowedValuesForProperties")
    @XmlElement(name="allowedValuesForProperty")
    private HashSet<XmlBasedAllowValueHelperImpl> allowedValuesForProperty = new HashSet<XmlBasedAllowValueHelperImpl>();

    @XmlElementWrapper(name = "sizesForCollectionProperties")
    @XmlElement(name="collection")
    private HashSet<XmlBasedPropertySizeHelperImpl> minMaxPropertySizes = new HashSet<XmlBasedPropertySizeHelperImpl>();

    public String getSoR() {
        return this.sor;
    }

    @Override
    public String getName() {
        return this.internalName;
    }

    @Override
    public String getDescription() {
        return this.internalDescription;
    }

    public boolean isInboundInterfaceAllowed(final Interfaces interfaces) {
        return this.interfaces.contains(interfaces);
    }

    public Map<Interfaces, String> getNotificationSchemesByInterface() {
        return this.interfaceToNotificationSchemeMapping;
    }

    public boolean isAllowedValueForProperty(final String property, final String value) {
        for (final XmlBasedAllowValueHelperImpl xml : this.allowedValuesForProperty) {
            if (xml.supportsProperty(property)) {
                return xml.isAllowedValueForProperty(property, value);
            }
        }

        return true;
    }

    public boolean isRequiredProperty(final String property) {
        return this.requiredProperties.contains(property);
    }

    public boolean isDisallowedProperty(final String property) {
        return this.disallowedProperties.contains(property);
    }

    public boolean isWithinRequiredSize(final String property, final Collection collection) {
        for (final XmlBasedPropertySizeHelperImpl helper : this.minMaxPropertySizes) {
            if (helper.supportsProperty(property)) {
                return helper.isWithinRequiredRangeForProperty(collection.size());
            }
        }

        return true;
    }

    @Override
    public boolean isWithinRequiredSize(final String property, final Map map) {
        for (final XmlBasedPropertySizeHelperImpl helper : this.minMaxPropertySizes) {
            if (helper.supportsProperty(property)) {
                return helper.isWithinRequiredRangeForProperty(map.size());
            }
        }

        return true;
    }

    public void setSor(final String sor) {
        this.sor = sor;
    }

    public void setRequiredProperties(final HashSet<String> requiredProperties) {
        this.requiredProperties = requiredProperties;
    }

    public void setDisallowedProperties(final HashSet<String> disallowedProperties) {
        this.disallowedProperties = disallowedProperties;
    }

    public void setInterfaces(final HashSet<Interfaces> interfaces) {
        this.interfaces = interfaces;
    }

    public void setInterfaceToNotificationSchemeMapping(final Map<Interfaces, String> interfaceToNotificationSchemeMapping) {
        this.interfaceToNotificationSchemeMapping = interfaceToNotificationSchemeMapping;
    }

    public void setAllowedValuesForProperty(final HashSet<XmlBasedAllowValueHelperImpl> allowedValuesForProperty) {
        this.allowedValuesForProperty = allowedValuesForProperty;
    }

    public void setMinMaxPropertySizes(final HashSet<XmlBasedPropertySizeHelperImpl> minMaxPropertySizes) {
        this.minMaxPropertySizes = minMaxPropertySizes;
    }

    public void setName(final String name) {
        this.internalName = name;
    }

    public void setDescription(final String description) {
        this.internalDescription = description;
    }
}
