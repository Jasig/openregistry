package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.internal.Entity;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public class JpaRegionImpl extends Entity implements Region {

    private String name;

    private String code;

    private JpaCountryImpl country;

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public Country getCountry() {
        return this.country;
    }
}
