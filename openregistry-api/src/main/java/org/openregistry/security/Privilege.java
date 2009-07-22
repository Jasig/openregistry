package org.openregistry.security;

/**
 * A Rule is the combination of a permission, the SoR its relevant for, and the user or set of users its being applied to.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
public interface Privilege extends Permission, Subject, Comparable<Privilege> {


}
