package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * @since 1.0
 */
public interface RoleInfo extends Serializable {

    String getTitle();

    Department getDepartment();

    Campus getCampus();

    Type getAffiliationType();

    String getCode();
}
