package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 * // TODO implement
 */
public class JpaIdentifierTypeImpl extends Entity implements IdentifierType {

    private Long id;

    private String name;

    protected Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
