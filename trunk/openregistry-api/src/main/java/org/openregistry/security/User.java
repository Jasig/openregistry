package org.openregistry.security;

import java.util.Set;
import java.util.Date;

/**
 * Represents a user within the OpenRegistry System.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface User {

    /**
     * A common name to refer to the user by.  This is generally used for display purposes only, but adds a little bit of "personalization."
     * @return the nickname.  CANNOT be NULL.
     */
    String getNickName();

    /**
     * Returns the set of systems of records that a user has access to.
     * @return the set of SoRs the user has access to.  CANNOT be NULL.  CAN be EMPTY.
     */
    Set<SoRInterface> getSystemOfRecords();

    /**
     * Gets the last time the user logged in.  CAN be NULL.
     * @return the last time the user logged in.
     */
    Date getLastLoggedIn();

    /**
     * The last host the user logged in from.  If the HOST can't be determined, it should be the last IP Address.  If {@link #getLastLoggedIn()} is NOT NULL, this CANNOT be NULL.
     * @return the last host the user logged in from.  CAN be NULL ONLY if {@link #getLastLoggedIn()} is NULL.
     */
    String getLastLoggedInHost();

    /**
     * Returns the username of the user.
     * @return the username of the user.  CANNOT be NULL.
     */
    String getUsername();
}
