package org.openregistry.core.domain;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface OrganizationalUnit {

    Long getId();

    Type getOrganizationalUnitType();

    String getLocalCode();

    String getName();

    Campus getCampus();

    OrganizationalUnit getParentOrganizationalUnit();
}
