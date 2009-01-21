package org.openregistry.core.domain;

import java.net.URL;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 * 
 */
public interface Url {

    Long getId();

    Type getType();

    URL getUrl();

    void setType(Type type);

    void setURL(URL url);
}
