package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Identifier;
import org.openregistry.core.domain.IdentifierType;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaIdentifierImpl implements Identifier {

    private JpaIdentifierTypeImpl type;

    private String value;

    public IdentifierType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }
}
