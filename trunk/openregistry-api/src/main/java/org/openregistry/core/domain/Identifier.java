package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Identifier extends Serializable {

    IdentifierType getType();

    String getValue();

    void setType(IdentifierType type);

    void setValue(String value);
}
