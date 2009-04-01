package org.openregistry.core.domain;

import java.net.URL;
import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 * 
 */
public interface Url extends Serializable {

    Type getType();

    URL getUrl();

    void setType(Type type);

    void setURL(URL url);
}
