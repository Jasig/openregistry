package org.openregistry.core.web.resources.representations;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Date;

/**
 * Simple struct-like class encapsulating the representation of a <i>Role</i> in the registry
 * for the purpose of exposing it via RESTful resources. It should not be used for any other purposes.
 * This class is marshalled into an XML representation using JAXB.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-role")
public final class RoleRepresentation {

    @XmlAttribute(name = "start-date")
    public Date startDate;

    @XmlAttribute(name = "end-date")
    public Date endDate;

    @XmlAttribute
    public String percentage;

    @XmlElementWrapper
    @XmlElement(name = "address")
    public List<Address> addresses;

    @XmlElementWrapper
    @XmlElement(name = "email")
    public List<Email> emails;

    @XmlElementWrapper
    @XmlElement(name = "url")
    public List<Url> urls;

    @XmlElementWrapper
    @XmlElement(name = "phone")
    public List<Phone> phones;


    @XmlRootElement
    public static class Address {

        @XmlAttribute
        public String type;

        @XmlElement
        public String line1;
        @XmlElement
        public String line2;

        @XmlElement
        public String line3;

        @XmlElement
        public String city;

        @XmlElement(name="postal-code")
        public String postalCode;

        @XmlElement(name="country-code")
        public String countryCode;

        @XmlElement(name="region-code")
        public String regionCode;
    }

    @XmlRootElement
    public static class Email {

        @XmlAttribute
        public String type;

        @XmlAttribute
        public String address;
    }

    @XmlRootElement
    public static class Url {

        @XmlAttribute
        public String type;

        @XmlAttribute
        public String address;
    }

    @XmlRootElement
    public static class Phone {

        @XmlAttribute
        public String type;

        @XmlAttribute(name = "address-type")
        public String addressType;

        @XmlElement(name = "country-code")
        public String countryCode;

        @XmlElement(name = "area-code")
        public String areaCode;

        @XmlElement
        public String number;

        @XmlElement
        public String extension;
    }
}
