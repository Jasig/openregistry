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
import java.util.List;

/**
 * Simple Java Bean encapsulating the representation of persons in the registry
 * for the purpose of exposing it via RESTful resources.
 * This is class is marshalled into an XML representation using JAXB.
 * <p/>
 * This class is immutable
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-person-ref")
public class PersonResponseRepresentation implements Serializable {

    @XmlElementWrapper(name = "identifiers")
    @XmlElement(name = "identifier")
    List<PersonIdentifierRepresentation> identifiers;

    /**
     * Required by JAXB
     */
    public PersonResponseRepresentation() {
    }

    public PersonResponseRepresentation(List<PersonIdentifierRepresentation> ids) {
        this.identifiers = ids;
    }

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
}
