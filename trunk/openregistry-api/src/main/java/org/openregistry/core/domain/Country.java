package org.openregistry.core.domain;

import java.util.List;
import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Country extends Serializable {

    Long getId();

    String getCode();

    String getName();

    List<Region> getRegions();
}
