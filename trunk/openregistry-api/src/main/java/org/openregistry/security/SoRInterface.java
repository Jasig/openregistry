package org.openregistry.security;

/**
 * Represents the link between a System of Record and the available interfaces for a user.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface SoRInterface {

    /**
     * Defines the various ways one can input data into the System.
     */
    enum Interface {REAL, WEBUI, BATCH}

    /**
     * The System of Record we're interested in.  CANNOT be NULL.
     * @return the system of record.
     */
    String getSystemOfRecord();

    /**
     * The list of interfaces that the user is allowed to access the system of record via.
     * @return the list of interfaces.  CANNOT be NULL.  CAN be of size 0, but that doesn't make much sense.
     */
    Interface[] getInterfaces();
}
