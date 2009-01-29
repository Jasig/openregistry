package org.openregistry.core.domain;

/**
 * @since 1.0
 */
public interface RoleInfo {

    String getTitle();

    Department getDepartment();

    Campus getCampus();

    Type getAffiliationType();

    String getCode();
}
