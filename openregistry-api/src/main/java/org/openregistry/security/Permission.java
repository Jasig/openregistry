package org.openregistry.security;

/**
 * Represents a permission applied to a resource.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Permission {

    enum PermissionType {CREATE, READ, UPDATE, DELETE}

    /**
     * The actual resource we're working with.  In most cases this will be an expression.
     * TODO: detail more about permissions here
     * @return the permission, CANNOT be NULL.
     */
    String getResource();

    /**
     * An optional textual description to provide more information to anyone looking at what the intention of the rule was.
     * @return the description, or null.
     */
    String getDescription();

    /**
     * Determines whether you can create for this permission.
     * <p>
     * NOTE: Applying a "CREATE" permission to anything other than major components (i.e. person.addresses) doesn't make any sense.
     * </p>
     * @return true, if yes, false otherwise.
     */
    boolean canCreate();

    /**
     * Determines whether you can read for this permission.
     *
     * @return true if yes, false otherwise.
     */
    boolean canRead();

    /**
     * Determines whether you can update for this permission.
     *
     * @return true, if yes, false otherwise.
     */
    boolean canUpdate();

    /**
     * Determines whether you can delete or not.
     * <p>
     * For instances of particular attributes, this means making them NULL???.   For addresses and such, it means deleting them.
     *
     * @return true, if yes, false otherwise.
     */
    boolean canDelete();
    
}
