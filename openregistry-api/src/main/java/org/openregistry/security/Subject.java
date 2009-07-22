package org.openregistry.security;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Jul 21, 2009
 * Time: 1:12:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Subject {

    enum PermissionType {EVERYONE, AUTHENTICATED, EXPRESSION, USER}

    /**
     * MUST be set if UserType == USER
     * @return the user identifier, or null.
     */
    String getUser();

    /**
     * Must be set if UserType = EXPRESSION.
     *
     * @return the expression, or NULL.
     */
    String getExpression();

    /**
     * Returns the type of permission this is.  I.e. for a user, for everyone, etc.
     *
     * @return the type of permission. CANNOT be NULL.
     */
    PermissionType getPermissionType();

    /**
     * Represents the system of record.  A NULL SoR indicates this permission applies to
     * @return the system of record or NULL, if the permission applies to none.
     */
    SystemOfRecord getSystemOfRecord();

}
