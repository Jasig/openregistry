package org.openregistry.core.domain;

import java.util.List;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Country {

    String getCode();

    String getName();

    List<? extends Region> getRegions();
}
