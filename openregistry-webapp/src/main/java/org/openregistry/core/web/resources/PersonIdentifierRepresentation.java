package org.openregistry.core.web.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Simple Java Bean encapsulating the representation of an identifier for a person in the registry
 * for the purpose of exposing it via RESTful resources.
 * This is class is marshalled into an XML representation using JAXB.
 * <p/>
 * This class is immutable
 *
 * @author Dmitriy Kopylenko
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
