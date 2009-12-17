package org.jasig.openregistry.core.domain.sor;

import org.openregistry.core.domain.sor.SoRSpecification;
import org.openregistry.core.domain.sor.SystemOfRecord;

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

    @XmlElement(name="name",nillable = false)
    private String sor;

    @XmlElementWrapper(name = "requiredProperties", required = false)
    @XmlElement(name = "property",nillable = false)
    private HashSet<String> requiredProperties = new HashSet<String>();

    @XmlElementWrapper(name = "disallowedProperties", required = false)
    @XmlElement(name = "property", nillable = false)
    private HashSet<String> disallowedProperties = new HashSet<String>();

    @XmlElementWrapper(name = "allowedInterfaces",required = true)
    @XmlElement(name = "interface", nillable = false)    
    private HashSet<SystemOfRecord.Interfaces> interfaces = new HashSet<SystemOfRecord.Interfaces>();

    @XmlElementWrapper(name = "notificationSchemes", required= false)
    private Map<SystemOfRecord.Interfaces, String> interfaceToNotificationSchemeMapping = new HashMap<SystemOfRecord.Interfaces, String>();

    @XmlElementWrapper(name = "allowedValues")
    private Map<String, HashSet<String>> allowedValuesForProperty = new HashMap<String, HashSet<String>>();

    public String getSoR() {
        return this.sor;
    }

    public boolean isInboundInterfaceAllowed(final SystemOfRecord.Interfaces interfaces) {
        return this.interfaces.contains(interfaces);
    }

    public Map<SystemOfRecord.Interfaces, String> getNotificationSchemesByInterface() {
        return this.interfaceToNotificationSchemeMapping;
    }

    public boolean isAllowedValueForProperty(final String property, final String value) {
        final Set<String> allowedValues = this.allowedValuesForProperty.get(property);

        return allowedValues == null || allowedValues.isEmpty() || allowedValues.contains(value);
    }

    public boolean isRequiredProperty(final String property) {
        return this.requiredProperties.contains(property);
    }

    public boolean isDisallowedProperty(final String property) {
        return this.disallowedProperties.contains(property);
    }

    public boolean isWithinRequiredSize(final String property, final Collection collection) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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

    public void setInterfaces(final HashSet<SystemOfRecord.Interfaces> interfaces) {
        this.interfaces = interfaces;
    }

    public void setInterfaceToNotificationSchemeMapping(final Map<SystemOfRecord.Interfaces, String> interfaceToNotificationSchemeMapping) {
        this.interfaceToNotificationSchemeMapping = interfaceToNotificationSchemeMapping;
    }

    public void setAllowedValuesForProperty(final Map<String, HashSet<String>> allowedValuesForProperty) {
        this.allowedValuesForProperty = allowedValuesForProperty;
    }
}
