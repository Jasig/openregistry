/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openregistry.core.web.resources.representations;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple Java Bean encapsulating the representation of persons in the registry
 * for the purpose of exposing it via RESTful resources.
 * This is class is marshalled into an XML representation using JAXB.
 * <p/>
 *
 */
@XmlRootElement(name = "open-registry-person-ref")
public class PersonResponseRepresentation implements Serializable {

    /**
     * Required by JAXB
     */
    public PersonResponseRepresentation() {
    }

    public PersonResponseRepresentation(List<PersonIdentifierRepresentation> ids) {
        this.identifiers = ids;
    }

    @XmlElementWrapper(name = "names")
    @XmlElement(name = "name")
    public List<PersonResponseRepresentation.Name> names = new ArrayList<PersonResponseRepresentation.Name>();

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

        /**
         * Required by JAXB
         */
        public Name() {
        }

        public Name(String nameType, String firstName,
                                    String lastName, String middleName, String prefix,
                                    String suffix) {
            this.nameType = nameType;
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }

    @XmlElement(name = "dob")
    public Date dateOfBirth;

    @XmlElement
    public String gender;

    @XmlElementWrapper(name = "identifiers")
    @XmlElement(name = "identifier")
    public List<PersonIdentifierRepresentation> identifiers;

    @XmlRootElement
    public static class PersonIdentifierRepresentation {

        @XmlAttribute
        String type;

        @XmlValue
        String value;

        /**
         * Required by JAXB
         */
        public PersonIdentifierRepresentation() {
        }

        public PersonIdentifierRepresentation(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    @XmlElement
    public String phi;

    @XmlElementWrapper(name = "idcards")
    @XmlElement(name = "idcard")
    public List<IdcardRepresentation> idcards = new ArrayList<IdcardRepresentation>();

    @XmlRootElement
    public static class IdcardRepresentation {

        @XmlElement
        public String rcn;

        @XmlElement
        public String cvc;

        @XmlElement
        public String barcode;

        @XmlElement
        public String iClass;

        @XmlElement
        public Date createdDate;

        @XmlElement
        public Date expirationDate;

        @XmlElement
        public Date updatedDate;

        /**
         * Required by JAXB
         */
        public IdcardRepresentation() {
        }

        public IdcardRepresentation(String rcn, String cvc,
                                    String barcode, String iClass, Date createdDate,
                                    Date expirationDate, Date updatedDate) {
            this.rcn = rcn;
            this.cvc = cvc;
            this.barcode = barcode;
            this.iClass = iClass;
            this.createdDate = createdDate;
            this.expirationDate = expirationDate;
            this.updatedDate = updatedDate;
        }
    }

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    public List<SimpleRoleRepresentation> roles = new ArrayList<SimpleRoleRepresentation>();

    @XmlRootElement
    public static class SimpleRoleRepresentation {

        @XmlElement
        public String roleType;

        @XmlElement
        public String title;


        @XmlElement
        public String department;

        @XmlElement
        public String organizationCode;

        @XmlElement
        public String isRBHS;

        @XmlElement
        public String status;

        @XmlElement
        public Date startDate;

        @XmlElement
        public Date endDate;

        /**
         * Required by JAXB
         */
        public SimpleRoleRepresentation() {
        }

        public SimpleRoleRepresentation(String roleType, String title,
                                        String department, String organizationCode, String isRBHS,
                                        String status, Date startDate,
                                        Date endDate) {
            this.roleType = roleType;
            this.title = title;
            this.department = department;
            this.organizationCode = organizationCode;
            this.isRBHS = isRBHS;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

}
