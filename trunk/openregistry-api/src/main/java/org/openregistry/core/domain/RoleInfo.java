package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @since 1.0
 */
public interface RoleInfo extends Serializable {

    String getTitle();

    OrganizationalUnit getOrganizationalUnit();

    Campus getCampus();

    Type getAffiliationType();

    String getCode();
}
