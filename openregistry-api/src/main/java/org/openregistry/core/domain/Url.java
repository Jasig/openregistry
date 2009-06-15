package org.openregistry.core.domain;

import java.net.URL;
import java.io.Serializable;

/**
 * Represents a URL that a person may have associated with their role.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 * 
 */
public interface Url extends Serializable {

    Long getId();
    
    /**
     * Defines the type of URL this is, i.e. personal, research, etc.
     * @return the type.  CANNOT be null.
     */
    Type getType();

    /**
     * The actual URL value.  This CANNOT be null.
     * @return the URL value.  CANNOT be null.
     */
    URL getUrl();

    /**
     * Sets the type of this URL.
     *
     * @param type the type of the URL.  Cannot be NULL.
     */
    void setType(Type type);

    /**
     * Sets the url value.
     * @param url the URL value.  CANNOT be null.
     */
    void setURL(URL url);
}
