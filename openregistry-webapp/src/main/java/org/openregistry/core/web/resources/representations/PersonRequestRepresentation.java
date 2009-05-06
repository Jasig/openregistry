package org.openregistry.core.web.resources.representations;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;

/**
 * A simple struct-like class encapsulating an incoming request for addition to the registry of
 * a person record from typical upstream systems of record.
 * This class should <b>only</b> be used to unmarshal serialized representation of an incoming person record.
 * The unmarshaling of the person XML representation is done automatically by JAXB.
 * <p/>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-person")
public class PersonRequestRepresentation {

    /* Required fields *************/
    @XmlAttribute(name = "sor")
    public String systemOfRecordId;

    @XmlAttribute(name = "sor-person-id")
    public String systemOfRecordPersonId;

    @XmlAttribute(name = "first-name")
    public String firstName;

    @XmlAttribute(name = "last-name")
    public String lastName;

    @XmlAttribute(name = "email")
    public String email;

    @XmlAttribute(name = "phone")
    public String phoneNumber;

    /* Optional fields *************/
    @XmlElement(name = "dob")
    public Date dateOfBirth;

    @XmlElement
    public String ssn;

    @XmlElement
    public String gender;

    @XmlElement(name = "address-line1")
    public String addressLine1;

    @XmlElement(name = "address-line2")
    public String addressLine2;

    @XmlElement
    public String city;

    @XmlElement
    public String region;

    @XmlElement(name = "postal-code")
    public String postalCode;

    public boolean checkRequiredData() {
        return (this.systemOfRecordId != null
                && this.systemOfRecordPersonId != null
                && this.firstName != null
                && this.lastName != null
                && this.email != null
                && this.phoneNumber != null);
    }
}
