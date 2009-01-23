package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 * TODO implement
 */
public class JpaCountryImpl extends Entity implements Country {

    private Long id;

    private String code;

    private String name;

    protected Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
