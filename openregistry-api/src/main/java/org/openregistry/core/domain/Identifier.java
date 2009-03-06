package org.openregistry.core.domain;

/**
 * 
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Identifier {

    IdentifierType getType();

    String getValue();

    void setType(IdentifierType type);

    void setValue(String value);
}
