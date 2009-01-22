package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaIdentifierTypeImpl extends Entity implements IdentifierType {

    private String name;

    public String getName() {
        return this.name;
    }
}
