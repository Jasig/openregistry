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
package org.openregistry.core.web.resources.representations;

import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Phone;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @XmlAttribute(name = "sor")
    public String systemOfRecordId;

    @XmlAttribute(name = "sor-person-id")
    public String systemOfRecordPersonId;

    @XmlElement(name = "dob")
    public Date dateOfBirth;

    @XmlElement
    public String ssn;

    @XmlElement
    public String gender;

    @XmlElementWrapper(name = "names")
    @XmlElement(name = "name")
    public List<Name> names = new ArrayList<Name>();

    @XmlRootElement(name = "name")
    public static class Name {

        @XmlAttribute(name = "type")
        public String nameType;

        @XmlAttribute(name = "first")
        public String firstName;

        @XmlAttribute(name = "last")
        public String lastName;

        @XmlAttribute(name = "middle")
        public String middleName;

        @XmlAttribute(name = "prefix")
        public String prefix;

        @XmlAttribute(name = "suffix")
        public String suffix;
    }

    @XmlElement (name = "reconciliation")
    public Reconciliation reconciliation;

    @XmlRootElement(name = "reconciliation")
    public static class Reconciliation {
        @XmlElement(name = "address")
        public Address address;

        @XmlRootElement(name = "address")
        public static class Address {

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

        }

        @XmlElementWrapper(name = "emails")
        @XmlElement(name = "email")
        public List<Email> emails = new ArrayList<Email>();

        @XmlRootElement(name = "email")
        public static class Email {
            @XmlValue
            public String email;
        }

        @XmlElementWrapper(name = "phones")
        @XmlElement(name = "phone")
        public List<Phone> phones = new ArrayList<Phone>();
        @XmlRootElement(name = "phone")
        public static class Phone {
            @XmlValue
            public String phoneNumber;
        }

        @XmlElementWrapper(name = "identifiers")
        @XmlElement(name = "identifier")
        public List<Identifier> identifiers = new ArrayList<Identifier>();

        @XmlRootElement(name = "identifier")
        public static class Identifier {
            @XmlValue
            public String identifierValue;

            @XmlAttribute(name = "type")
            public String identifierType;
        }
    }

    @Override
    public String toString() {
        return "PersonRequestRepresentation{" +
                "systemOfRecordId='" + systemOfRecordId + '\'' +
                ", systemOfRecordPersonId='" + systemOfRecordPersonId + '\'' +
                ", firstName='" + names.get(0).firstName + '\'' +
                ", lastName='" + names.get(0).lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", ssn='" + ssn + '\'' +
                ", gender='" + gender + '\'' +
                //", email='" + email + '\'' +
                //", phoneNumber='" + phoneNumber + '\'' +
                //", addressLine1='" + addressLine1 + '\'' +
                //", addressLine2='" + addressLine2 + '\'' +
                //", city='" + city + '\'' +
                //", region='" + region + '\'' +
                //", postalCode='" + postalCode + '\'' +
                '}';
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forNewPerson() {
        return newRepresentationWithRequiredDataAndSsn("new");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forExistingPersonNoSoRRecord() {
        return newRepresentationWithRequiredDataAndSsn("existing");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forExistingPersonExistingSoRRecord() {
        return newRepresentationWithRequiredDataAndSsn("existingError");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation withValidationErrors() {
        return newRepresentationWithRequiredDataAndSsn("errors");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forMultiplePeople() {
        return newRepresentationWithRequiredDataAndSsn("multiple");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forLinkingSorPersonGood() {
        return newRepresentationWithRequiredDataAndSsn("link-sor-good");
    }

    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @return
     */
    public static PersonRequestRepresentation forLinkingSorPersonBad() {
        return newRepresentationWithRequiredDataAndSsn("link-sor-bad");
    }


    /**
     * Factory method for use only for mocking purposes in tests
     *
     * @param ssn
     * @return
     */
    private static PersonRequestRepresentation newRepresentationWithRequiredDataAndSsn(String ssn) {
        //For test purposes, distinguish between 'new', 'exact match', or 'multiple people found'
        // by setting the 'ssn' property
        final PersonRequestRepresentation rep = new PersonRequestRepresentation();
        rep.systemOfRecordId = "test";
        rep.systemOfRecordPersonId = "test";
        Name name = new Name();
        name.nameType = "FORMAL";
        name.firstName = "test";
        name.lastName = "test";
        name.prefix = "test";
        name.suffix = "test";
        name.middleName = "test";
        rep.names.add(name);
        Reconciliation.Email email = new Reconciliation.Email();
        email.email = "test";
        Reconciliation.Phone phone = new Reconciliation.Phone();
        phone.phoneNumber = "test";
        rep.ssn = ssn;
        return rep;
    }

    public static PersonRequestRepresentation modifyRepresentationWithRequiredData(){
        final PersonRequestRepresentation rep = new PersonRequestRepresentation();
        Name name = new Name();
        name.nameType = "FORMAL";
        name.firstName = "test";
        name.lastName = "test";
        name.prefix = "test";
        name.suffix = "test";
        name.middleName = "test";
        rep.names.add(name);
        rep.ssn = "xxx-xx-xxxx";
        rep.gender="F";
        rep.dateOfBirth=new Date();
        return rep;
    }
}
