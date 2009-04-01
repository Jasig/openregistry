package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Region extends Serializable {

    String getName();

    String getCode();

    Country getCountry();
}
