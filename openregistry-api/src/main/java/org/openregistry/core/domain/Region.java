package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * Represents a region within a nation state (country). Some countries/nation states may not have regions.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Region extends Serializable {

    /**
     * The name of the region.
     *
     * @return the name, cannot be NULL.
     */
    String getName();

    /**
     * The code for the region.
     *
     * @return the code for the region.  CANNOT be NULL.
     */
    String getCode();

    /**
     * Refers to the country this region is valid for.
     *
     * @return the country this region is valid for.  CANNOT be NULL.
     */
    Country getCountry();
}
