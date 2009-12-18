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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * A simple struct-like class representing a generic errors response which might arise
 * from calling OpenRegistry RESTful resources.
 * This class should <b>only</b> be used to marshal XML representation of generic errors response.
 * The marshaling of the errors response XML representation is done automatically by JAXB.
 * <p/>
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-errors")
public class ErrorsResponseRepresentation {

    /**
     * Required by JAXB provider
     */
    public ErrorsResponseRepresentation() {
    }

    public ErrorsResponseRepresentation(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @XmlElement(name = "error")
    public List<String> errorMessages;
}
