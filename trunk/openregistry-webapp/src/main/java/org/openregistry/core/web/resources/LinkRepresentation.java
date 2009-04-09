package org.openregistry.core.web.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Simple Java Bean encapsulating the representation of a collection of link XHTML fragments
 * for the purpose of exposing it via RESTful resources.
 * This is class is marshalled into an XML representation using JAXB.
 * <p/>
 * This class is immutable
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-resources")
class LinkRepresentation {

    @XmlElement(name = "link")
    List<Link> links;

    /**
     * Required by JAXB
     */
    LinkRepresentation() {
    }

    LinkRepresentation(List<Link> links) {
        this.links = links;
    }

    @XmlRootElement
    static class Link {
        @XmlAttribute
        String rel;

        @XmlAttribute
        String href;

        Link() {
        }

        Link(String rel, String href) {
            this.rel = rel;
            this.href = href;
        }
    }
}