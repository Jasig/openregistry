package org.openregistry.core.domain;

import java.util.List;
import java.io.Serializable;

/**
 * Refers to a nation-state.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Country extends Serializable {

    /**
     * The internal identifier for this country.
     *
     * @return the internal identifier.  CANNOT be NULL.
     */
    Long getId();

    /**
     * The code representing this country.
     * @return the code, CANNOT be NULL.
     */
    String getCode();

    /**
     * The internationalized name of the country.
     *
     * @return the name of the country. CANNOT be NULL.
     */
    String getName();

    /**
     * The list of regions associated with this country.
     * @return the list of regions associated with this country. CANNOT be NULL.  CAN be EMPTY.
     */
    List<Region> getRegions();
}
