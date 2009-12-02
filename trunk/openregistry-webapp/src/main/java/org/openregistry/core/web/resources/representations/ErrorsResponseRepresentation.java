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

    @XmlElement(name = "error")
    public List<String> errorMessage;
}
