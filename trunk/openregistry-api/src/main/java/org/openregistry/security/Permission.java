package org.openregistry.security;

/**
 * Represents a permission applied to a particular SOR and Interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Permission {

    enum PermissionType {AUTHENTICATED, EVERYONE, USER, EXPRESSION}

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
    String getSystemOfRecord();

    /**
     * The actual permission.  TODO: detail more about permissions here
     * @return the permission, CANNOT be NULL.
     */
    String getPermission();

    /**
     * An optional textual description to provide more information to anyone looking at what the intention of the rule was.
     * @return the description, or null.
     */
    String getDescription();
    
}
