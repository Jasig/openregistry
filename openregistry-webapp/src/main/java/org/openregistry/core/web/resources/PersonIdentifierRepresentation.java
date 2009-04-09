package org.openregistry.core.web.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @since 1.0
 */
@XmlRootElement
public class PersonIdentifierRepresentation {

    @XmlAttribute
    String type;

    @XmlAttribute
    String value;

    /**
     * Required by JAXB
     */
    PersonIdentifierRepresentation() {
    }

    PersonIdentifierRepresentation(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
