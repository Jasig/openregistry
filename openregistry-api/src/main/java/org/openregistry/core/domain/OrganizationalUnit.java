package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface OrganizationalUnit extends Serializable {

    Long getId();

    Type getOrganizationalUnitType();

    String getLocalCode();

    String getName();

    Campus getCampus();

    OrganizationalUnit getParentOrganizationalUnit();
}
