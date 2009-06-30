package org.openregistry.security;

/**
 * Represents a permission applied to a particular SOR and Interface.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Permission {

    /**
     * The various ways to interact with the system.
     */
    enum Interface {REAL, BATCH, WEBUI}

    /**
     * Represents the system of record.  A NULL SoR indicates this permission applies to 
     * @return the system of record or NULL, if the permission applies to none.
     */
    String getSystemOfRecord();

    /**
     * The list of interfaces supported by this permission.  CANNOT be NULL.  CAN be EMPTY.  Empty implies the rule
     * applies to the calculated information.
     * @return the interfaces this permission applies to.
     */
    Interface[] getInterfaces();

    /**
     * The actual permission.  TODO: detail more about permissions here
     * @return the permission, CANNOT be NULL.
     */
    String getPermission();
    
}
