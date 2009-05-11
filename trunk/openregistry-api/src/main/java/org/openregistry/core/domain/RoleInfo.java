package org.openregistry.core.domain;

import java.io.Serializable;

/**
 * Represents the specific role that a person can hold.  The {@link org.openregistry.core.domain.Role} class is an
 * "instance" of the RoleInfo with additional information (such as Start Date).
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 */
public interface RoleInfo extends Serializable {

    /**
     * Returns the title of the Role.
     *
     * @return the title.  CANNOT be NULL.
     */
    String getTitle();

    /**
     * The organizational unit that this role belongs to.
     *
     * @return the organizational unit. CANNOT be NULL.
     */
    OrganizationalUnit getOrganizationalUnit();

    /**
     * The campus this role is affiliated with.
     *
     * @return the campus.  CANNOT be NULL.
     */
    Campus getCampus();

    /**
     * The affiliation of this role (i.e. Student, Staff, Faculty, etc.)
     *
     * @return the affiliation.  CANNOT be NULL.
     */
    Type getAffiliationType();

    /**
     * The local code representing this role.
     * @return the local code.
     */
    String getCode();

    /**
     * A name to display for the roleinfo.
     * @return
     */
    String getDisplayableName();

    Long getId();
}
